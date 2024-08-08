INSERT INTO USERS VALUES (100, 'Pankaj', 'About-1', 'pankaj@maverick.com', '1234');
INSERT INTO USERS VALUES (200, 'Ajay', 'About-2', 'ajay@maverick.com', '1234');
INSERT INTO USERS VALUES (300, 'Mritunjay', 'About-3', 'mritunjay@maverick.com', '1234');


INSERT INTO CATEGORIES (CATEGORY_ID, TITLE, DESCRIPTION) VALUES (1000, 'Programming', 'All about programming');
INSERT INTO CATEGORIES (CATEGORY_ID, TITLE, DESCRIPTION) VALUES (2000, 'Politics', 'All about politics');


INSERT INTO POSTS (POST_ID, CATEGORY_ID, USER_ID, ADDED_DATE, POST_TITLE, CONTENT, IMAGE_NAME) 
VALUES (10000, 1000, 100, CURRENT_DATE(), 'What is a Programming Language', 'Programming Language is used to communicate with machine.', 'default.png');

INSERT INTO POSTS (POST_ID, CATEGORY_ID, USER_ID, ADDED_DATE, POST_TITLE, CONTENT, IMAGE_NAME) 
VALUES (20000, 2000, 100, CURRENT_DATE(), 'What is Politics', 'Do not know.', 'default.png');

INSERT INTO POSTS (POST_ID, CATEGORY_ID, USER_ID, ADDED_DATE, POST_TITLE, CONTENT, IMAGE_NAME) 
VALUES (30000, 1000, 200, CURRENT_DATE(), 'What is a Programming Language', 'Programming Language is used to communicate with machine.', 'default.png');

/* These roles will be added when applications run
INSERT INTO ROLE (ID, NAME) VALUES (100000, 'ROLE_ADMIN');
INSERT INTO ROLE (ID, NAME) VALUES (200000, 'ROLE_NORMAL');

INSERT INTO USER_ROLE (ROLE_ID, USER_ID) VALUES(100000, 100);
INSERT INTO USER_ROLE (ROLE_ID, USER_ID) VALUES(200000, 200);
*/
