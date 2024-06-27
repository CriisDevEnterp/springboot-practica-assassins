-- Inserts para la tabla `roles`
INSERT INTO `roles` (`name`) VALUES ('ROLE_USER');
INSERT INTO `roles` (`name`) VALUES ('ROLE_ADMIN');
INSERT INTO `roles` (`name`) VALUES ('ROLE_RECEPCIONIST');

-- Inserts para la tabla `users`
INSERT INTO `users` (`username`, `password`, `enabled`, `first_name`, `last_name`, `email`, `create_at`, `profile_image`)
VALUES ('david', '$2a$10$h36sqOFp/2P9PH8rI.SaZedB5WOTRLxkBnNqaXMIcTzamOW/j.xpC', 1, 'David', 'Ramirez', 'david@prueba.com', CURRENT_TIMESTAMP, 'static/images/no-user.jpg');

INSERT INTO `users` (`username`, `password`, `enabled`, `first_name`, `last_name`, `email`, `create_at`, `profile_image`)
VALUES ('emmanuel', '$2a$10$h36sqOFp/2P9PH8rI.SaZedB5WOTRLxkBnNqaXMIcTzamOW/j.xpC', 1, 'Emmanuel', 'Lopez', 'emmanuel@prueba.com', CURRENT_TIMESTAMP, 'static/images/no-user.jpg');

INSERT INTO `users` (`username`, `password`, `enabled`, `first_name`, `last_name`, `email`, `create_at`, `profile_image`)
VALUES ('ricardo', '$2a$10$h36sqOFp/2P9PH8rI.SaZedB5WOTRLxkBnNqaXMIcTzamOW/j.xpC', 1, 'Ricardo', 'Castillo', 'ricardo@prueba.com', CURRENT_TIMESTAMP, 'static/images/no-user.jpg');

INSERT INTO `users` (`username`, `password`, `enabled`, `first_name`, `last_name`, `email`, `create_at`, `profile_image`)
VALUES ('fabian', '$2a$10$h36sqOFp/2P9PH8rI.SaZedB5WOTRLxkBnNqaXMIcTzamOW/j.xpC', 1, 'Fabian', 'Santillan', 'fabian@prueba.com', CURRENT_TIMESTAMP, 'static/images/no-user.jpg');

INSERT INTO `users` (`username`, `password`, `enabled`, `first_name`, `last_name`, `email`, `create_at`, `profile_image`)
VALUES ('ernesto', '$2a$10$h36sqOFp/2P9PH8rI.SaZedB5WOTRLxkBnNqaXMIcTzamOW/j.xpC', 1, 'Ernesto', 'Ramos', 'ernesto@prueba.com', CURRENT_TIMESTAMP, 'static/images/no-user.jpg');

INSERT INTO `users` (`username`, `password`, `enabled`, `first_name`, `last_name`, `email`, `create_at`, `profile_image`)
VALUES ('brian', '$2a$10$h36sqOFp/2P9PH8rI.SaZedB5WOTRLxkBnNqaXMIcTzamOW/j.xpC', 1, 'Brian', 'Hernandez', 'brian@prueba.com', CURRENT_TIMESTAMP, 'static/images/no-user.jpg');

INSERT INTO `users` (`username`, `password`, `enabled`, `first_name`, `last_name`, `email`, `create_at`, `profile_image`)
VALUES ('cristopher', '$2a$10$h36sqOFp/2P9PH8rI.SaZedB5WOTRLxkBnNqaXMIcTzamOW/j.xpC', 1, 'Cristopher', 'Hernandez', 'cristopher@prueba.com', CURRENT_TIMESTAMP, 'static/images/no-user.jpg');

INSERT INTO `users` (`username`, `password`, `enabled`, `first_name`, `last_name`, `email`, `create_at`, `profile_image`)
VALUES ('luis', '$2a$10$h36sqOFp/2P9PH8rI.SaZedB5WOTRLxkBnNqaXMIcTzamOW/j.xpC', 1, 'Luis', 'Torres', 'luis@prueba.com', CURRENT_TIMESTAMP, 'static/images/no-user.jpg');

INSERT INTO `users` (`username`, `password`, `enabled`, `first_name`, `last_name`, `email`, `create_at`, `profile_image`)
VALUES ('yorman', '$2a$10$h36sqOFp/2P9PH8rI.SaZedB5WOTRLxkBnNqaXMIcTzamOW/j.xpC', 1, 'Yorman', 'Rojas', 'yorman@prueba.com', CURRENT_TIMESTAMP, 'static/images/no-user.jpg');


-- Inserts para la tabla `users_roles`
-- Asignando roles a los usuarios creados
INSERT INTO `users_roles` (`user_id`, `role_id`)
VALUES (1, 1); -- Asignando ROLE_USER al usuario con id 1
INSERT INTO `users_roles` (`user_id`, `role_id`)
VALUES (1, 2); -- Asignando ROLE_ADMIN al usuario con id 1

INSERT INTO `users_roles` (`user_id`, `role_id`)
VALUES (2, 1); -- Asignando ROLE_USER al usuario con id 2

INSERT INTO `users_roles` (`user_id`, `role_id`)
VALUES (3, 1); -- Asignando ROLE_USER al usuario con id 3
INSERT INTO `users_roles` (`user_id`, `role_id`)
VALUES (3, 2); -- Asignando ROLE_ADMIN al usuario con id 3

INSERT INTO `users_roles` (`user_id`, `role_id`)
VALUES (4, 1); -- Asignando ROLE_USER al usuario con id 4
INSERT INTO `users_roles` (`user_id`, `role_id`)
VALUES (4, 2); -- Asignando ROLE_ADMIN al usuario con id 4

INSERT INTO `users_roles` (`user_id`, `role_id`)
VALUES (5, 1); -- Asignando ROLE_USER al usuario con id 5
INSERT INTO `users_roles` (`user_id`, `role_id`)
VALUES (5, 2); -- Asignando ROLE_ADMIN al usuario con id 5

INSERT INTO `users_roles` (`user_id`, `role_id`)
VALUES (6, 1); -- Asignando ROLE_USER al usuario con id 6

INSERT INTO `users_roles` (`user_id`, `role_id`)
VALUES (7, 1); -- Asignando ROLE_USER al usuario con id 7
INSERT INTO `users_roles` (`user_id`, `role_id`)
VALUES (7, 2); -- Asignando ROLE_ADMIN al usuario con id 7

INSERT INTO `users_roles` (`user_id`, `role_id`)
VALUES (8, 1); -- Asignando ROLE_USER al usuario con id 8
INSERT INTO `users_roles` (`user_id`, `role_id`)
VALUES (8, 3); -- Asignando ROLE_RECEPCIONIST al usuario con id 8

INSERT INTO `users_roles` (`user_id`, `role_id`)
VALUES (9, 1); -- Asignando ROLE_USER al usuario con id 9
INSERT INTO `users_roles` (`user_id`, `role_id`)
VALUES (9, 3); -- Asignando ROLE_RECEPCIONIST al usuario con id 9
