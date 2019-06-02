package gb.vhs.api.entity.response;

public class View {

    public interface AuthUser extends Base {}
    public interface User extends Base, UserExt, Users_UserRoles {}







    public interface Base {}

    public interface UserExt {}
    public interface Users_UserRoles {}
    public interface U_M {}    // User 1 - * Messages
    public interface U_I {}  // User * - * Time Intervals
    public interface U_P {}   // User 1 - * UserProfile
    public interface U_PA {} // User 1 - * PerformedActivities


    public interface M_U {}  // Messages * - 1 User
    public interface M_MT {}  // Messages * - 1 Message Template
    public interface M_F {}  // Message 1 - * MessageFeedback
    public interface M_A {}  // Message * - 1 Activity

    public interface T_U {}  // Property Schemes * - * Activity Schemes

    public interface P_U {}  // UserProfile * - 1 User

    public interface MT_M {}  // User 1 - * Message Templates

    public interface F_M {}   // MessageFeedback * - 1 Message

    public interface A_PA {} // Activity 1 - * PerformedActivity
    public interface A_M {} // Activity 1 - * Messages

    public interface PA_U {} // PerformedActivity * - 1 User
    public interface PA_A {} // PerformedActivity * - 1 Activity

}