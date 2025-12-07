-- 1. Effort Types
INSERT IGNORE INTO `effort_type` (`id`, `effort_type_name`, `description`) VALUES
(1, 'Dynamic', 'Focus on moving heavy load with high velocity'),
(2, 'Max', 'Focus on lifting the heaviest load possible'),
(3, 'Volume', 'Focus on completing as many repetitions as possible'),
(4, 'Speed', 'Focus on achieving high velocity and rapid acceleration');

-- 2. Exercise Categories
INSERT IGNORE INTO `exercise_category` (`id`, `category_name`) VALUES
(1, 'Global'),
(2, 'Compound'),
(3, 'Isolated');

-- 3. Exercises
INSERT IGNORE INTO `exercise` (`id`, `name`, `description`, `is_bodyweight`, `exercise_category_id`) VALUES
(1, 'Back Squat', 'Squat performed in a bilateral stance...', FALSE, 1),
(2, 'Bench Press', 'Pressing in the laying position...', FALSE, 1),
(3, 'Deadlift', 'Performed in a conventional or a sumo stance...', FALSE, 1),
(4, 'Front Squat', 'Performed with a barbell placed on shoulders...', FALSE, 1),
(5, 'Overhead press', 'Performed in a standing position...', FALSE, 2),
(6, 'Pull-up', 'Pull-up performed conventionally...', TRUE, 2),
(7, 'Lunges', 'Unilateral squat performed wth one leg...', FALSE, 1),
(8, 'Triceps pushdown', 'Performed with a cable machine...', FALSE, 3),
(9, 'Back extensions', 'Performed on a roman chair bench...', TRUE, 3);

-- 4. Training Methods
INSERT IGNORE INTO `training_method` (`id`, `name`, `duration_of_cycle`, `description`) VALUES
(1, 'RecordAccumulatorTest', 1, 'Method for accumulating records'),
(2, '5x5', 4, 'Training method based on performing 5 working sets...'),
(3, '5x30', 1, 'Training method focused on energic hip movements...'),
(4, 'Texas Method', 2, 'Training method that requires switching...'),
(5, 'Condense Conjugate', 2, 'Training method based on mixing max effort...'),
(6, 'Custom', 0, 'Custom Training Method');

-- 5. Movement Patterns
INSERT IGNORE INTO `movement_pattern` (`id`, `name`) VALUES
(1, 'Flexion'), (2, 'Extension'), (3, 'Rotation'), (4, 'Anti-Flexion'),
(5, 'Anti-Extension'), (6, 'Anti-Rotation'), (7, 'Vertical Pull'),
(8, 'Horizontal Pull'), (9, 'Hip Hinge'), (10, 'Squat'), (11, 'Horizontal Push'),
(12, 'Vertical Push'), (13, 'Incline Push'), (14, 'Decline Push'),
(15, 'Raise'), (16, 'Lunge');

-- 6. Target Muscle Groups
INSERT IGNORE INTO `target_muscle_group` (`id`, `name`) VALUES
(1, 'Biceps'), (2, 'Triceps'), (3, 'Shoulders'), (4, 'Pectorals'),
(5, 'Forearms'), (6, 'Neck'), (7, 'Upper back'), (8, 'Lats'),
(9, 'Lower back'), (10, 'Glutes'), (11, 'Adductors'), (12, 'Hip flexors'),
(13, 'Abdominals'), (14, 'Hamstrings'), (15, 'Quadriceps'), (16, 'Calves');

-- 7. Exercise <-> Movement Pattern
INSERT IGNORE INTO `exercise_has_movement_pattern` (`exercise_id`, `movement_pattern_id`) VALUES
(1, 2), (1, 4), (1, 5), (1, 10), (2, 4), (2, 11), (3, 2), (3, 4), (3, 9),
(4, 2), (4, 4), (4, 5), (4, 10), (5, 2), (5, 4), (5, 5), (5, 12),
(6, 1), (6, 7), (7, 2), (7, 6), (7, 9), (7, 10), (7, 16),
(8, 2), (8, 14), (9, 2), (9, 4), (9, 5), (9, 9);

-- 8. Exercise <-> Muscle Group
INSERT IGNORE INTO `exercise_target_muscle_group` (`exercise_id`, `target_muscle_group_id`) VALUES
(1, 7), (1, 9), (1, 10), (1, 11), (1, 13), (1, 14), (1, 15), (1, 16),
(2, 2), (2, 3), (2, 4), (2, 5), (2, 7), (2, 8), (2, 9), (2, 10),
(3, 5), (3, 6), (3, 7), (3, 8), (3, 9), (3, 10), (3, 11), (3, 13), (3, 14), (3, 15), (3, 16),
(4, 3), (4, 5), (4, 7), (4, 9), (4, 10), (4, 11), (4, 13), (4, 14), (4, 15), (4, 16),
(5, 2), (5, 3), (5, 5), (5, 6), (5, 7), (5, 9), (5, 10), (5, 13),
(6, 1), (6, 3), (6, 5), (6, 7), (6, 8), (6, 13),
(7, 7), (7, 9), (7, 10), (7, 11), (7, 13), (7, 14), (7, 15), (7, 16),
(8, 2), (8, 8), (9, 9), (9, 10), (9, 13), (9, 14);

-- 9. Users
INSERT IGNORE INTO `user` (`id`, `username`, `password`, `email`, `role`, `status`, `create_date`) VALUES
(1, 'admin', '$2a$10$8miWJMx0pLlKyP8OS.key..9r4eFzx7YbAQTakOvUkYOMV9bFkVtC', 'admin@powerstrong.com', 'ADMIN', 'ACTIVE', '2025-01-01'),
(2, 'user1', '$2a$10$1miAQbsVL1Zw0rEUxYxHauePjbE.wqoL6h9EscZWno9Oxj2ePIUWu', 'user1@example.com', 'USER', 'ACTIVE', '2025-01-01'),
(3, 'user2', '$2a$10$OM5fDwvdMeRoaomNl/NtPuBBKEXqyUz3sor95K6wBeijU3rnFFxTy', 'user2@example.com', 'USER', 'BLOCKED', '2025-02-28'),
(4, 'user3', '$2a$10$VNuT4lHMUs8XBNKNAlnBMeZ7VWFNYhMgy8qxIfS1yYRgld2GjZ/Ma', 'user3@example.com', 'USER', 'INACTIVE', '2025-07-14');

-- 10. Training Plans (Templates)
INSERT IGNORE INTO `training_plan` (`id`, `name`, `training_method_id`, `is_preset`, `created_by_user_id`, `created_date`) VALUES
(1, 'RecordAccumulatorTest', 1, TRUE, 1, '2025-01-01'),
(2, '3-day 5x5', 2, TRUE, 1, '2025-01-01'),
(3, '3-day Texas Method', 4, TRUE, 1, '2025-01-01'),
(4, '2-day Condense Conjugate', 5, TRUE, 1, '2025-01-01'),
(5, '2-day Custom', 6, FALSE, 2, '2025-01-02');

-- 11. User Training Plans (Links)
INSERT IGNORE INTO `user_training_plan`
(`id`, `user_id`, `training_plan_id`, `start_date`, `end_date`, `status`, `was_tracking_nutrition`, `was_tracking_sleep`, `average_hours_of_sleep`, `personal_evaluation`)
VALUES
(1, 2, 1, '2025-01-01', '2025-01-01', 'COMPLETED', FALSE, FALSE, 0, 0),
(2, 3, 1, '2025-02-28', '2025-02-28', 'COMPLETED', FALSE, FALSE, 0, 0),
(3, 4, 1, '2025-07-14', '2025-07-14', 'COMPLETED', FALSE, FALSE, 0, 0),
(4, 2, 5, '2026-01-05', '2026-01-25', 'CANCELLED', FALSE, FALSE, 0, 0),
(5, 2, 4, '2025-10-06', '2025-11-01', 'ACTIVE', TRUE, TRUE, 8, 10);

-- 12. Training Days
INSERT IGNORE INTO `training_day` (`id`, `week_number`, `day_name`, `day_order`, `training_plan_id`, `days_gap`) VALUES
(1, 1, 'RecordAccumulatorTest', 1, 1, 0),
(2, 1, 'Monday', 1, 2, 0), (3, 1, 'Wednesday', 2, 2, 1), (4, 1, 'Friday', 3, 2, 1),
(5, 1, 'Monday', 1, 3, 0), (6, 1, 'Wednesday', 2, 3, 1), (7, 1, 'Friday', 3, 3, 1),
(8, 2, 'Monday', 4, 3, 2), (9, 2, 'Wednesday', 5, 3, 1), (10, 2, 'Friday', 6, 3, 1),
(11, 1, 'Tuesday', 1, 4, 0), (12, 1, 'Thursday', 2, 4, 1),
(13, 2, 'Tuesday', 3, 4, 4), (14, 2, 'Thursday', 4, 4, 1),
(15, 3, 'Tuesday', 5, 4, 4), (16, 3, 'Thursday', 6, 4, 1),
(17, 4, 'Tuesday', 7, 4, 4), (18, 4, 'Thursday', 8, 4, 1),
(19, 1, 'Tuesday', 1, 5, 0), (20, 1, 'Friday', 2, 5, 2),
(21, 1, 'Tuesday', 1, 5, 0), (22, 1, 'Friday', 2, 5, 2),
(23, 1, 'Tuesday', 1, 5, 0), (24, 1, 'Friday', 2, 5, 2);

-- 13. User Exercise Max
INSERT IGNORE INTO `user_exercise_max` (`user_id`, `exercise_id`, `current_one_rep_max`) VALUES
(2, 1, 110.0), (2, 2, 90.0), (2, 3, 150.0), (2, 4, 80.0), (2, 5, 60.0), (2, 6, 0.0), (2, 8, 40.0);

-- 14. Planned Exercises
INSERT IGNORE INTO `planned_exercise` (`id`, `exercise_order`, `planned_reps`, `planned_sets`, `intensity_percentage`, `exercise_id`, `training_day_id`, `effort_type_id`) VALUES
(1, 1, 1, 1, NULL, 1, 1, 2), (2, 2, 1, 1, NULL, 2, 1, 2), (3, 3, 1, 1, NULL, 3, 1, 2),
(4, 1, 5, 5, NULL, 1, 2, 2), (5, 1, 5, 5, NULL, 2, 3, 2), (6, 1, 5, 5, NULL, 3, 4, 2),
(7, 1, 3, 3, NULL, 1, 5, 2), (8, 2, 8, 3, NULL, 4, 5, 3),
(9, 1, 3, 3, NULL, 2, 6, 2), (10, 2, 8, 3, NULL, 5, 6, 3),
(11, 1, 3, 3, NULL, 3, 7, 2), (12, 2, 8, 3, NULL, 6, 7, 3),
(13, 1, 1, 3, NULL, 1, 8, 2), (14, 2, 7, 3, NULL, 4, 8, 3),
(15, 1, 1, 3, NULL, 2, 9, 2), (16, 2, 7, 3, NULL, 5, 9, 3),
(17, 1, 1, 3, NULL, 3, 10, 2), (18, 2, 7, 3, NULL, 6, 10, 3),
(19, 1, 1, 3, NULL, 2, 11, 2), (20, 2, 3, 3, NULL, 4, 11, 1), (21, 3, 3, 3, NULL, 6, 11, 2), (22, 4, 5, 3, NULL, 9, 11, 4),
(23, 1, 1, 3, NULL, 3, 12, 2), (24, 2, 3, 3, NULL, 5, 12, 1), (25, 3, 3, 3, NULL, 7, 12, 2), (26, 4, 5, 3, NULL, 6, 12, 4),
(27, 1, 1, 3, NULL, 6, 13, 2), (28, 2, 3, 3, NULL, 7, 13, 1), (29, 3, 3, 3, NULL, 5, 13, 2), (30, 4, 5, 3, NULL, 3, 13, 4),
(31, 1, 1, 3, NULL, 1, 14, 2), (32, 2, 3, 3, NULL, 2, 14, 1), (33, 3, 3, 3, NULL, 4, 14, 2), (34, 4, 5, 3, NULL, 8, 14, 4),
(35, 1, 1, 3, NULL, 2, 15, 2), (36, 2, 3, 3, NULL, 4, 15, 1), (37, 3, 3, 3, NULL, 6, 15, 2), (38, 4, 5, 3, NULL, 9, 15, 4),
(39, 1, 1, 3, NULL, 3, 16, 2), (40, 2, 3, 3, NULL, 5, 16, 1), (41, 3, 3, 3, NULL, 7, 16, 2), (42, 4, 5, 3, NULL, 6, 16, 4),
(43, 1, 1, 3, NULL, 6, 17, 2), (44, 2, 3, 3, NULL, 7, 17, 1), (45, 3, 3, 3, NULL, 5, 17, 2), (46, 4, 5, 3, NULL, 3, 17, 4),
(47, 1, 1, 3, NULL, 1, 18, 2), (48, 2, 3, 3, NULL, 2, 18, 1), (49, 3, 3, 3, NULL, 4, 18, 2), (50, 4, 5, 3, NULL, 8, 18, 4),
(51, 1, 5, 3, 0.80, 3, 19, 3), (52, 2, 8, 3, 0.60, 1, 19, 3), (53, 3, 10, 3, 0.55, 4, 19, 3),
(54, 1, 5, 3, 0.70, 2, 20, 3), (55, 2, 8, 3, NULL, 6, 20, 3), (56, 3, 8, 3, 0.65, 5, 20, 3), (57, 4, 12, 3, 0.60, 8, 20, 3),
(58, 1, 5, 3, 0.85, 3, 21, 3), (59, 2, 8, 3, 0.65, 1, 21, 3), (60, 3, 10, 3, 0.60, 4, 21, 3),
(61, 1, 5, 3, 0.75, 2, 22, 3), (62, 2, 8, 3, NULL, 6, 22, 3), (63, 3, 8, 3, 0.70, 5, 22, 3), (64, 4, 12, 3, 0.65, 8, 22, 3),
(65, 1, 5, 3, 0.90, 3, 23, 3), (66, 2, 8, 3, 0.70, 1, 23, 3), (67, 3, 10, 3, 0.65, 4, 23, 3),
(68, 1, 5, 3, 0.80, 2, 24, 3), (69, 2, 8, 3, NULL, 6, 24, 3), (70, 3, 8, 3, 0.75, 5, 24, 3), (71, 4, 12, 3, 0.70, 8, 24, 3);

-- 15. Executed Sets
-- ID=1: RecordAccumulatorTest User2
INSERT IGNORE INTO `executed_set` (`set_number`, `executed_reps`, `weight_used`, `execution_date`, `user_training_plan_id`, `planned_exercise_id`) VALUES
(1, 1, 100, '2025-01-01', 1, 1),
(1, 1, 82.5, '2025-01-01', 1, 2),
(1, 1, 140, '2025-01-01', 1, 3);

-- ID=2: RecordAccumulatorTest User3
INSERT IGNORE INTO `executed_set` (`set_number`, `executed_reps`, `weight_used`, `execution_date`, `user_training_plan_id`, `planned_exercise_id`) VALUES
(1, 1, 120, '2025-02-28', 2, 1),
(1, 1, 100, '2025-02-28', 2, 2),
(1, 1, 130, '2025-02-28', 2, 3);

-- ID=3: RecordAccumulatorTest User4
INSERT IGNORE INTO `executed_set` (`set_number`, `executed_reps`, `weight_used`, `execution_date`, `user_training_plan_id`, `planned_exercise_id`) VALUES
(1, 1, 195, '2025-07-14', 3, 1),
(1, 1, 125, '2025-07-14', 3, 2),
(1, 1, 220, '2025-07-14', 3, 3);

-- ID=5: Condense Conjugate User2 (ACTIVE)
INSERT IGNORE INTO `executed_set` (`set_number`, `executed_reps`, `weight_used`, `execution_date`, `user_training_plan_id`, `planned_exercise_id`) VALUES
-- Tydzień 1 Dzień 1 (19)
(1, 1, 80, '2025-10-07', 5, 19), (2, 1, 80, '2025-10-07', 5, 19), (3, 1, 80, '2025-10-07', 5, 19),
(4, 3, 75, '2025-10-07', 5, 20), (5, 3, 75, '2025-10-07', 5, 20), (6, 2, 75, '2025-10-07', 5, 20),
(7, 3, 10, '2025-10-07', 5, 21), (8, 3, 10, '2025-10-07', 5, 21), (9, 3, 10, '2025-10-07', 5, 21),
(10, 7, 30, '2025-10-07', 5, 22), (11, 7, 30, '2025-10-07', 5, 22), (12, 7, 30, '2025-10-07', 5, 22),
-- Tydzień 1 Dzień 2 (23)
(1, 1, 135, '2025-10-09', 5, 23), (2, 1, 135, '2025-10-09', 5, 23), (3, 1, 135, '2025-10-09', 5, 23),
(4, 3, 45, '2025-10-09', 5, 24), (5, 2, 45, '2025-10-09', 5, 24), (6, 2, 45, '2025-10-09', 5, 24),
(7, 3, 70, '2025-10-09', 5, 25), (8, 3, 70, '2025-10-09', 5, 25), (9, 3, 70, '2025-10-09', 5, 25),
(10, 5, 5, '2025-10-09', 5, 26), (11, 4, 5, '2025-10-09', 5, 26), (12, 3, 5, '2025-10-09', 5, 26),
-- Tydzień 2 Dzień 1 (27)
(1, 1, 15, '2025-10-14', 5, 27), (2, 1, 15, '2025-10-14', 5, 27), (3, 0, 15, '2025-10-14', 5, 27),
(4, 3, 60, '2025-10-14', 5, 28), (5, 3, 60, '2025-10-14', 5, 28), (6, 2, 60, '2025-10-14', 5, 28),
(7, 3, 50, '2025-10-14', 5, 29), (8, 3, 50, '2025-10-14', 5, 29), (9, 3, 50, '2025-10-14', 5, 29),
(10, 5, 80, '2025-10-14', 5, 30), (11, 5, 80, '2025-10-14', 5, 30), (12, 4, 80, '2025-10-14', 5, 30),
-- Tydzień 2 Dzień 2 (31)
(1, 1, 95, '2025-10-16', 5, 31), (2, 1, 95, '2025-10-16', 5, 31), (3, 1, 95, '2025-10-16', 5, 31),
(4, 3, 65, '2025-10-16', 5, 32), (5, 3, 65, '2025-10-16', 5, 32), (6, 3, 65, '2025-10-16', 5, 32),
(7, 3, 80, '2025-10-16', 5, 33), (8, 2, 80, '2025-10-16', 5, 33), (9, 3, 80, '2025-10-16', 5, 33),
(10, 5, 30, '2025-10-16', 5, 34), (11, 5, 30, '2025-10-16', 5, 34), (12, 5, 30, '2025-10-16', 5, 34),
-- Tydzień 3 Dzień 1 (35)
(1, 1, 85, '2025-10-21', 5, 35), (2, 1, 85, '2025-10-21', 5, 35), (3, 1, 85, '2025-10-21', 5, 35),
(4, 3, 80, '2025-10-21', 5, 36), (5, 2, 80, '2025-10-21', 5, 36), (6, 2, 80, '2025-10-21', 5, 36),
(7, 3, 12.5, '2025-10-21', 5, 37), (8, 3, 12.5, '2025-10-21', 5, 37), (9, 3, 12.5, '2025-10-21', 5, 37),
(10, 5, 40, '2025-10-21', 5, 38), (11, 5, 40, '2025-10-21', 5, 38), (12, 5, 40, '2025-10-21', 5, 38),
-- Tydzień 3 Dzień 2 (39)
(1, 1, 145, '2025-10-23', 5, 39), (2, 1, 145, '2025-10-23', 5, 39), (3, 1, 145, '2025-10-23', 5, 39),
(4, 3, 47.5, '2025-10-23', 5, 40), (5, 2, 47.5, '2025-10-23', 5, 40), (6, 2, 47.5, '2025-10-23', 5, 40),
(7, 3, 75, '2025-10-23', 5, 41), (8, 3, 75, '2025-10-23', 5, 41), (9, 3, 75, '2025-10-23', 5, 41),
(10, 5, 5, '2025-10-23', 5, 42), (11, 5, 5, '2025-10-23', 5, 42), (12, 5, 5, '2025-10-23', 5, 42),
-- Tydzień 4 Dzień 1 (43)
(1, 1, 15, '2025-10-28', 5, 43), (2, 1, 15, '2025-10-28', 5, 43), (3, 1, 15, '2025-10-28', 5, 43),
(4, 3, 65, '2025-10-28', 5, 44), (5, 3, 65, '2025-10-28', 5, 44), (6, 2, 65, '2025-10-28', 5, 44),
(7, 3, 52.5, '2025-10-28', 5, 45), (8, 3, 52.5, '2025-10-28', 5, 45), (9, 3, 52.5, '2025-10-28', 5, 45),
(10, 5, 85, '2025-10-28', 5, 46), (11, 5, 85, '2025-10-28', 5, 46), (12, 4, 85, '2025-10-28', 5, 46),
-- Tydzień 4 Dzień 2 (47)
(1, 1, 105, '2025-10-30', 5, 47), (2, 1, 105, '2025-10-30', 5, 47), (3, 1, 105, '2025-10-30', 5, 47),
(4, 3, 70, '2025-10-30', 5, 48), (5, 3, 70, '2025-10-30', 5, 48), (6, 2, 70, '2025-10-30', 5, 48),
(7, 3, 85, '2025-10-30', 5, 49), (8, 3, 85, '2025-10-30', 5, 49), (9, 3, 85, '2025-10-30', 5, 49),
(10, 4, 32.5, '2025-10-30', 5, 50), (11, 5, 32.5, '2025-10-30', 5, 50), (12, 5, 32.5, '2025-10-30', 5, 50);