INSERT INTO `time_interval` (`id`, `day_of_week`, `day_segment`, `from_date`, `to_date`) VALUES (1, 'MONDAY', 'MORNING', '09:00:00', '12:00:00'), (2, 'MONDAY', 'AFTERNOON', '12:01:00', '17:00:00'), (3, 'MONDAY', 'EVENING', '17:01:00', '20:00:00'), (4, 'TUESDAY', 'MORNING', '09:00:00', '12:00:00'), (5, 'TUESDAY', 'AFTERNOON', '12:01:00', '17:00:00'), (6, 'TUESDAY', 'EVENING', '17:01:00', '20:00:00'), (7, 'WEDNESDAY', 'MORNING', '09:00:00', '12:00:00'), (8, 'WEDNESDAY', 'AFTERNOON', '12:01:00', '17:00:00'), (9, 'WEDNESDAY', 'EVENING', '17:01:00', '20:00:00'), (10, 'THURSDAY', 'MORNING', '09:00:00', '12:00:00'), (11, 'THURSDAY', 'AFTERNOON', '12:01:00', '17:00:00'), (12, 'THURSDAY', 'EVENING', '17:01:00', '20:00:00'), (13, 'FRIDAY', 'MORNING', '09:00:00', '12:00:00'), (14, 'FRIDAY', 'AFTERNOON', '12:01:00', '17:00:00'), (15, 'FRIDAY', 'EVENING', '17:01:00', '20:00:00'), (16, 'SATURDAY', 'MORNING', '09:00:00', '12:00:00'), (17, 'SATURDAY', 'AFTERNOON', '12:01:00', '17:00:00'), (18, 'SATURDAY', 'EVENING', '17:01:00', '20:00:00'), (19, 'SUNDAY', 'MORNING', '09:00:00', '12:00:00'), (20, 'SUNDAY', 'AFTERNOON', '12:01:00', '17:00:00'), (21, 'SUNDAY', 'EVENING', '17:01:00', '20:00:00');
INSERT INTO `activity`(`gamebus_id`, `activity_type`, `challenge_rule_name`) VALUES (1, 'WALKING', 'Fitpic with guide at a walking day lunch walk'),(2, 'WALKING', 'Fitpic doing groceries on foot'),(3, 'RUNNING', "Fitpic finishing a workout in the 'start-to-run' program"),(4, 'SOCIAL', 'Fitpic at the TU/e sports day'),(5, 'CYCLING', 'Fitpic with one or more colleagues cycling to work'),(6, 'CYCLING', 'Fitpic sweating in spinning class'),(7, 'CLASS', 'Fitpic at a Body Pump class'),(8, 'CLASS', 'Fitpic finishing any SSCE sports class'),(9, 'CLASS', 'Fitpic at a Hajraa open training'),(10, 'SOCIAL', 'Fitpic performing any activity with a group of participants [BONUS] '),(11, 'WALKING', 'Fitpic at a coffee machine in another TU/e building'),(12, 'WALKING', 'Fitpic at the staircase on the top floor in Atlas'),(13, 'WALKING', 'Fitpic at the Karpendonkse Plas (side furthest from TU/e)'),(14, 'RUNNING', 'Fitpic wearing running clothes in a park/the woods'),(15, 'RUNNING', 'Fitpic of a complete 30 minute treadmill program'),(16, 'RUNNING', 'Fitpic of any tracking app registering over 15k steps on one day'),(17, 'CYCLING', 'Fitpic doing groceries by bike'),(18, 'CYCLING', 'Fitpic with your bike at the nearest sports club'),(19, 'CYCLING', 'Fitpic of a Geocaching quest completed this week'),(20, 'SWIMMING', 'Fitpic wearing swimming goggles in/around a pool'),(21, 'DESKERCISE', 'Fitpic doing 10 air-squats'),(22, 'FITNESS', 'Fitpic at the gym doing pull-ups'),(23, 'YOGA', 'Fitpic while doing a Yoga-pose'),(24, 'BALLSPORTS', 'Fitpic playing a game of basket ball at campus'),(25, 'BALLSPORTS', 'Fitpic while playing keepy uppy on campus'),(26, 'BALLSPORTS', 'Fitpic with any ball you brought to work'),(27, 'DESKERCISE', 'Fitpic while doing tricep dips at your desk');
INSERT INTO `message_template`(`template_key`, `activity_type`, `content`, `persuasion_profile`) VALUES ('EXTRAVERSION_1', 'RANDOM', "Did you know that your competition also has a newsfeed? Let's go out and take a *|activity|* today, and show everybody how hard you're working on your goals!", 'EXTRAVERSION'),('EXTRAVERSION_2', 'RANDOM', "Those who work hard you be rewarded. If you reach 20 points at the end of this week, you'll have a chance to win one of the limited edition GameBus cups! Let's go out and take a *|activity|* today and get one step closer to our weekly target.", 'EXTRAVERSION'),('EXTRAVERSION_3', 'RANDOM', "Hey champ, will you show everybody how fit you are? Let's go out and take a *|activity|* and climb that leaderboard under the watchful eyes of all those fellow-participants!", 'EXTRAVERSION'),('EXTRAVERSION_4', 'CLASS', 'Sports = connecting with friends. Joining a group lesson with a friend is one of the most popular ways to reach your daily required activity. Adding a group selfie even earns you bonus points. Call a friend and try it today.', 'EXTRAVERSION'),('EXTRAVERSION_5', 'RANDOM', "Living healthy is all about balance. If you work out, it's okay to treat yourself with some comfort food from time to time. Then, you can say you've earned it! Let's go out and take a *|activity|* today, so you can have a snack later.", 'EXTRAVERSION'),('AGREEABLENESS_1', 'RANDOM', "Being fit isn't always easy. Help a friend who finds it difficult to reach his/her weekly goals and invite him/her to do activities together. Ask him/her to go out and take *|activity|* together, for example.", 'AGREEABLENESS'),('AGREEABLENESS_2', 'RANDOM', 'Teamwork is fun, this also concerns working out. Going out to take a *|activity|* together with some friends earns everybody loads of points (including BONUS points if you make a group selfie), AND you will have a good time together!', 'AGREEABLENESS'),('AGREEABLENESS_3', 'WALKING', "Being active improves your mood. Are people grumpy on Mondays at the office? Invite them for short walking breaks or work walks, and you'll see that the atmosphere in the group will skyrocket!", 'AGREEABLENESS'),('AGREEABLENESS_4', 'RANDOM', "You would do us a big favor if you would log an activity today. Let's go out and take a *|activity|* , we will ensure you that you will feel great afterwards.", 'AGREEABLENESS'),('AGREEABLENESS_5', 'RANDOM', "You don't want to let your team down, they're counting on you! Let's go out soon and take a *|activity|*. You don't want to be a free-rider.", 'AGREEABLENESS'),('CONSCIENTIOUSNESS_1', 'RANDOM', "Will you claim your place among the top 10 fittest people of this experiment? Don't give up, let's go out and take a *|activity|*  today. You can do it!", 'CONSCIENTIOUSNESS'),('CONSCIENTIOUSNESS_2', 'WALKING', 'Where are you having lunch today? Tip: go for a walk while having lunch. This way, you will be active, save some time AND enjoy some fresh air in the process. Why not right?', 'CONSCIENTIOUSNESS'),('CONSCIENTIOUSNESS_3', 'RANDOM', "We can feel it, you're the kind of person who likes to work hard. Will you also reach your activity target this week? Let's go out and take a *|activity|* , secure those points, so you can cross that one off your to-do list.", 'CONSCIENTIOUSNESS'),('CONSCIENTIOUSNESS_4', 'RANDOM', "Hey champ, have you checked out the individual competition out already? Let's go out and take a *|activity|* today and show everybody what you're capable of.", 'CONSCIENTIOUSNESS'),('CONSCIENTIOUSNESS_5', 'SOCIAL', 'Have you tried work walks yet? Take your discussions outside and escape the meeting rooms. An average work walk will earn you 2 points + 4 BONUS points if you make a group selfie along with it!', 'CONSCIENTIOUSNESS'),('NEUROTICISM_1', 'RANDOM', "Did you know that a lack of physical activity can increase the risk of heart problems by 40%? Just 15-30 minutes of activity can already decrease that risk significantly. Let's go out and take a *|activity|* today to get the blood flowing!", 'NEUROTICISM'),('NEUROTICISM_2', 'RANDOM', "Less stress and a better mood? The solution is working out. Numerous studies have already proven that physical activity increases the release of dopamine in your blood, which makes you happy! Let's go out and take a *|activity|*  and induce a better mood. ", 'NEUROTICISM'),('NEUROTICISM_3', 'RANDOM', "Do you ever feel uncertain about your health? Don't worry, it's not that difficult. If you go out and take a *|activity|* soon we can ensure you that you'll be on the right track to being healthy.", 'NEUROTICISM'),('NEUROTICISM_4', 'DESKERCISE', "Do you ever feel sore and tight from sitting at the office all day? Try 15 minutes of deskersize and you'll see that you feel better because of it. Besides, you'll also burn some calories with it. Try it out sometime.", 'NEUROTICISM'),('NEUROTICISM_5', 'RANDOM', "Unfortunately, you'll lose your chance on receiving a reward if you don't reach your weekly targets. But no worries, we will help you through it. Let's go out and take a *|activity|* soon. You can do it, we believe in you!", 'NEUROTICISM'),('OPENNESS_1', 'RANDOM', "Sometimes you won't have a gym you can go to. However, you can still do some resistance training. From squatting with a bag of potatoes in your neck to doing bicep curls with a bottle of coke. Be creative and make a picture of your home-made gym!", 'OPENNESS'),('OPENNESS_2', 'FITNESS', "I have this feeling that you're the adventurous -type. Let's be adventurous and go out to take a *|activity|*. It will not only earn you points in GameBus, it will also earn you a fresh adrenaline-boost.", 'OPENNESS'),('OPENNESS_3', 'RANDOM', "Nearly any activity will earn you points in GameBus. Why not try a new sport soon? Maybe you'll discover a new talent you did not know about. We advise going out an taking a *|activity|*, have you tried it out?", 'OPENNESS'),('OPENNESS_4', 'RANDOM', "Did you know that GameBus recently got its own built-in activity tracker? No FitBit or other tracker is needed. Let's go out and take a *|activity|* soon and try it out!", 'OPENNESS'),('OPENNESS_5', 'RANDOM', 'Many health-experts see health gamification apps like GameBus as having great potential to revolutionize how we engage in physical activity. Log an activity like taking a *|activity|* soon and provide us with feedback so we can further improve the app.', 'OPENNESS');
INSERT INTO `user_profile`(`id`, `is_general`, `timestamp`, `extraversion_a`, `extraversion_b`, `agreeableness_a`, `agreeableness_b`, `conscientiousness_a`, `conscientiousness_b`, `neuroticism_a`, `neuroticism_b`, `openness_a`, `openness_b`) VALUES (9999, true, makedate(2019, 15), 1,1,1,1,1,1,1,1,1,1);