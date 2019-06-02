package gb.vhs.api.entity.enumeration;

public class Enum {

    public enum TreatmentGroup {
        CONTROL,
        TREATMENT
    }

    public enum PersuasionProfile {
        EXTRAVERSION,
        NEUROTICISM,
        AGREEABLENESS,
        OPENNESS,
        CONSCIENTIOUSNESS,
    }


    public enum MessageFeedback {
        POSITIVE_NO_ACTION,
        POSITIVE_YES_ACTION,

        NEGATIVE_TIME,
        NEGATIVE_FREQUENCY,
        NEGATIVE_ACTIVITY,
        NEGATIVE_RULES,
        NEGATIVE_PERSUASION_PROFILE,

    }

    public enum DaySegment {
        MORNING,
        AFTERNOON,
        EVENING
    }

    public enum ActivityTrigger {
        MESSAGE,
        LEADERBOARD,
        EVENT,
        SOCIAL,
        SERENDIPITY,
        OTHER
    }

    public enum ActivityType {
        WALKING,
        RUNNING,
        CYCLING,
        FITNESS,
        SWIMMING,
        YOGA,
        BALLSPORTS,
        DESKERCISE,
        SOCIAL,
        CLASS,
        RANDOM
    }

}

