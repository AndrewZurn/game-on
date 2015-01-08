# --- Initialize tags

# --- !Ups

INSERT INTO CATEGORY(CATEGORY_ID, CATEGORY_NAME) VALUES(1, "Action & Adventure");
INSERT INTO CATEGORY(CATEGORY_ID, CATEGORY_NAME) VALUES(2, "Classics");
INSERT INTO CATEGORY(CATEGORY_ID, CATEGORY_NAME) VALUES(3, "Family");
INSERT INTO CATEGORY(CATEGORY_ID, CATEGORY_NAME) VALUES(4, "Fighting");
INSERT INTO CATEGORY(CATEGORY_ID, CATEGORY_NAME) VALUES(5, "Kinect");
INSERT INTO CATEGORY(CATEGORY_ID, CATEGORY_NAME) VALUES(6, "Shooter");
INSERT INTO CATEGORY(CATEGORY_ID, CATEGORY_NAME) VALUES(7, "Sports");
INSERT INTO CATEGORY(CATEGORY_ID, CATEGORY_NAME) VALUES(8, "Strategy");

INSERT INTO GAME(GAME_ID, GAME_NAME, DETAILS_URL, IMAGE_URL, CATEGORY_ID, OWNED, CREATED)
	VALUES(1, 'Halo 4', 'http://marketplace.xbox.com/en-US/Product/Halo-4/66acd000-77fe-1000-9115-d8024d530919',
	'http://download.xbox.com/content/images/66acd000-77fe-1000-9115-d8024d530919/1033/boxartlg.jpg', 6, 0, now());
INSERT INTO GAME(GAME_ID, GAME_NAME, DETAILS_URL, IMAGE_URL, CATEGORY_ID, OWNED, CREATED) 
	VALUES(2, 'FIFA 15',  'http://marketplace.xbox.com/en-US/Product/FIFA-15/66acd000-77fe-1000-9115-d802454109db',
	'http://download.xbox.com/content/images/66acd000-77fe-1000-9115-d802454109db/1033/boxartlg.jpg', 7, 0, now());
INSERT INTO GAME(GAME_ID, GAME_NAME, DETAILS_URL, IMAGE_URL, CATEGORY_ID, OWNED, CREATED) 
	VALUES(3, 'Minecraft',  'http://marketplace.xbox.com/en-us/Product/Minecraft-Xbox-360-Edition/66acd000-77fe-1000-9115-d802584111f7',
	'http://download.xbox.com/content/images/66acd000-77fe-1000-9115-d802584111f7/1033/boxartlg.jpg', 1, 0, now());
INSERT INTO GAME(GAME_ID, GAME_NAME, DETAILS_URL, IMAGE_URL, CATEGORY_ID, OWNED, CREATED) 
	VALUES(4, 'Forza Horizon', 'http://marketplace.xbox.com/en-US/Product/Forza-Horizon/66acd000-77fe-1000-9115-d8024d5309c9',
	'http://download.xbox.com/content/images/66acd000-77fe-1000-9115-d8024d5309c9/1033/boxartlg.jpg', 7, 0, now());
INSERT INTO GAME(GAME_ID, GAME_NAME, DETAILS_URL, IMAGE_URL, CATEGORY_ID, OWNED, CREATED) 
	VALUES(5, 'The Sims 3', 'http://marketplace.xbox.com/en-US/Product/The-Sims-3/66acd000-77fe-1000-9115-d802454108e2',
	'http://download.xbox.com/content/images/66acd000-77fe-1000-9115-d802454108e2/1033/boxartlg.jpg', 8, 0, now());
INSERT INTO GAME(GAME_ID, GAME_NAME, DETAILS_URL, IMAGE_URL, CATEGORY_ID, OWNED, CREATED) 
	VALUES(6, 'Banjo-Kazooie', 'http://marketplace.xbox.com/en-US/Product/Banjo-Kazooie/66acd000-77fe-1000-9115-d80258410954',
	'http://download.xbox.com/content/images/66acd000-77fe-1000-9115-d8024d5307ed/1033/boxartlg.jpg', 2, 0, now());
INSERT INTO GAME(GAME_ID, GAME_NAME, DETAILS_URL, IMAGE_URL, CATEGORY_ID, OWNED, CREATED) 
	VALUES(7, 'LEGO Lord of the Rings', 'http://marketplace.xbox.com/en-US/Product/LEGO-Lord-of-the-Rings/66acd000-77fe-1000-9115-d8025752081d',
	'http://download.xbox.com/content/images/66acd000-77fe-1000-9115-d8025752081d/1033/boxartlg.jpg', 3, 0, now());
INSERT INTO GAME(GAME_ID, GAME_NAME, DETAILS_URL, IMAGE_URL, CATEGORY_ID, OWNED, CREATED) 
	VALUES(8, 'WWE 2K14', 'http://marketplace.xbox.com/en-US/Product/WWE-2K14/66acd000-77fe-1000-9115-d802545408b2',
	'http://download.xbox.com/content/images/66acd000-77fe-1000-9115-d802545408b2/1033/boxartlg.jpg', 4, 0, now());
INSERT INTO GAME(GAME_ID, GAME_NAME, DETAILS_URL, IMAGE_URL, CATEGORY_ID, OWNED, CREATED) 
	VALUES(9, 'Fruit Ninja Kinect', 'http://marketplace.xbox.com/en-US/Product/Fruit-Ninja-Kinect/66acd000-77fe-1000-9115-d80258410b79',
	'http://download.xbox.com/content/images/66acd000-77fe-1000-9115-d80258410b79/1033/boxartlg.jpg', 5, 0, now());
INSERT INTO GAME(GAME_ID, GAME_NAME, DETAILS_URL, IMAGE_URL, CATEGORY_ID, OWNED, CREATED) 
	VALUES(10, 'Call of Duty: Black Ops 2', 'http://marketplace.xbox.com/en-US/Product/COD-Black-Ops-II/66acd000-77fe-1000-9115-d802415608c3',
	'http://download.xbox.com/content/images/66acd000-77fe-1000-9115-d802415608c3/1033/boxartlg.jpg', 6, 0, now());
INSERT INTO GAME(GAME_ID, GAME_NAME, DETAILS_URL, IMAGE_URL, CATEGORY_ID, OWNED, CREATED) 
	VALUES(11, 'Skyrim', 'http://marketplace.xbox.com/en-US/Product/Skyrim/66acd000-77fe-1000-9115-d802425307e6',
	'http://download.xbox.com/content/images/66acd000-77fe-1000-9115-d802425307e6/1033/boxartlg.jpg', 1, 0, now());

INSERT INTO VOTES(VOTE_ID, GAME_ID, VOTE_CREATED) VALUES(1, 1, now());
INSERT INTO VOTES(VOTE_ID, GAME_ID, VOTE_CREATED) VALUES(2, 1, now());
INSERT INTO VOTES(VOTE_ID, GAME_ID, VOTE_CREATED) VALUES(3, 11, now());
INSERT INTO VOTES(VOTE_ID, GAME_ID, VOTE_CREATED) VALUES(4, 2, now());
INSERT INTO VOTES(VOTE_ID, GAME_ID, VOTE_CREATED) VALUES(5, 2, now());
INSERT INTO VOTES(VOTE_ID, GAME_ID, VOTE_CREATED) VALUES(6, 10, now());
INSERT INTO VOTES(VOTE_ID, GAME_ID, VOTE_CREATED) VALUES(7, 10, now());
INSERT INTO VOTES(VOTE_ID, GAME_ID, VOTE_CREATED) VALUES(8, 3, now());
INSERT INTO VOTES(VOTE_ID, GAME_ID, VOTE_CREATED) VALUES(9, 1, now());
INSERT INTO VOTES(VOTE_ID, GAME_ID, VOTE_CREATED) VALUES(10, 3, now());
INSERT INTO VOTES(VOTE_ID, GAME_ID, VOTE_CREATED) VALUES(11, 1, now());
INSERT INTO VOTES(VOTE_ID, GAME_ID, VOTE_CREATED) VALUES(12, 9, now());
INSERT INTO VOTES(VOTE_ID, GAME_ID, VOTE_CREATED) VALUES(13, 3, now());
INSERT INTO VOTES(VOTE_ID, GAME_ID, VOTE_CREATED) VALUES(14, 2, now());
INSERT INTO VOTES(VOTE_ID, GAME_ID, VOTE_CREATED) VALUES(15, 9, now());
INSERT INTO VOTES(VOTE_ID, GAME_ID, VOTE_CREATED) VALUES(16, 1, now());
INSERT INTO VOTES(VOTE_ID, GAME_ID, VOTE_CREATED) VALUES(17, 7, now());
INSERT INTO VOTES(VOTE_ID, GAME_ID, VOTE_CREATED) VALUES(18, 8, now());
INSERT INTO VOTES(VOTE_ID, GAME_ID, VOTE_CREATED) VALUES(19, 2, now());