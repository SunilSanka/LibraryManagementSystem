-- User
insert into user values(10001,sysdate(),'AB');
insert into user values(10002,sysdate(),'Jill');
insert into user values(10003,sysdate(),'Jam');

-- Book
insert into book values(11001,'ISBN001',10,'Pearson','First Book');
insert into book values(11002,'ISBN002',10,'McGrawHill','Second Book');
insert into book values(11003,'ISBN003',10,'McGrawHill','Third Book');

-- Feeedback
insert into feedback values(12001,'Good book',11001,10001);
insert into feedback values(12002,'Not recommended',11002,10001);
insert into feedback values(12003,'Nice book',11003,10001);

-- UserAccount
-- ID, BORROWEDBOOKS, FINE_AMOUNT, LOSTBOOKS, RESERVEDBOOKS, RETURNEDBOOKS,	USER_ID  
insert into user_account values(13001,0,0,0,0,0,10001);
insert into user_account values(13002,0,0,0,0,0,10002);
insert into user_account values(13003,0,0,0,0,0,10003);

-- BookReservations


