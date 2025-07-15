package com.company.hoanganh_thuctap_project.listener;

import com.company.hoanganh_thuctap_project.entity.OnboardingStatus;
import com.company.hoanganh_thuctap_project.entity.User;
import com.company.hoanganh_thuctap_project.entity.UserStep;
import io.jmix.core.DataManager;
import io.jmix.core.Id;
import io.jmix.core.event.EntityChangedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class UserStepEventListener {

    @Autowired
    private DataManager dataManager;


    //Khi một bản ghi UserStep bị xóa, hệ thống không còn tồn tại dữ liệu của bản ghi đó nữa,
    // nên không thể truy vấn trực tiếp để lấy các thông tin liên quan như đã liên kết với User nào.

    //Tuy nhiên, trong quá trình xóa, hệ thống có thể ghi nhận các thay đổi thông qua một sự kiện (event).
    // Trong đó, phương thức event.getChanges() chứa các thông tin về các thay đổi này.

    // event.getChanges().getOldReferenceId("user") để lấy lại ID của User liên kết trước khi bị xóa.
    @EventListener
    public void onUserStepChangedBeforeCommit(EntityChangedEvent<UserStep> event) {
        User user;
        if (event.getType() != EntityChangedEvent.Type.DELETED) {  // nếu loại thay đổi không phải xóa   ; event.getType()
                                                                   // nó sẽ trả về loại thay đổi của entity
            Id<UserStep> userStepId = event.getEntityId();  // lấy id của entity UserStep vừa thay đổi ; ebent.getEntityId()
                                                           // nó sẽ trả về id(primary key) của bản ghi thay đổi và lưu vào biến userStepId
            UserStep userStep = dataManager.load(userStepId).one();// load dữ liệu từ database bằng id vừa lấy và trả về cho đối tượng tương ứng
            user = userStep.getUser();// lấy đối tượng gắn với userStep đó ; getUser() là getter có sẵn nếu đặt mối quan hệ @ManyToOne từ UserStep -> User
        } else {
            // nếu sự kiện là xóa
            // khi userStep bị xóa mình không thể lấy truy vấn bản ghi này nữa lên ta lấy qua event bởi vì
            // mỗi lần thay đổi trong hệ thống sự kiện (event) có thể ghi nhận những thay đổi
            Id<User> userId = event.getChanges().getOldReferenceId("user"); // lấy id của user trươc khi xóa ()
            // getOldReferrenceId() lấy giá trị cũ của thuộc tính được them chiếu

            if (userId == null) {// nếu không có id ném lỗi
                throw new IllegalStateException("Cannot get User from deleted UserStep");
            }
            // nếu có lấy lại bản ghi
            user = dataManager.load(userId).one();// Datamanager
        }
        long completedCount = user.getSteps().stream()
                .filter(us -> us.getCompletedDate() != null)
                .count();
        if (completedCount == 0) {
            user.setOnboardingStatus(OnboardingStatus.NOT_STARTED);
        } else if (completedCount == user.getSteps().size()) {
            user.setOnboardingStatus(OnboardingStatus.COMPLETED);
        } else {
            user.setOnboardingStatus(OnboardingStatus.IN_PROGRESS);
        }
        dataManager.save(user);
    }
}