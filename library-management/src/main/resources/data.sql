-- User
insert into user values(10001,sysdate(),'AB');
insert into user values(10002,sysdate(),'Jill');
insert into user values(10003,sysdate(),'Jam');

-- Book
insert into book values(11001,'ISBN001',8,'Pearson','First Book');
insert into book values(11002,'ISBN002',9,'McGrawHill','Second Book');
insert into book values(11003,'ISBN003',10,'McGrawHill','Third Book');

-- Feeedback
insert into feedback values(12001,'Good book',11001,10001);
insert into feedback values(12002,'Not recommended',11002,10001);
insert into feedback values(12003,'Nice book',11003,10001);

-- UserAccount
-- ID, FINE_AMOUNT,LOSTBOOKS, REQUESTEDBOOKS, RESERVEDBOOKS, RETURNEDBOOKS, USER_ID 
insert into user_account values(13001,0,0,1,1,0,10001);
insert into user_account values(13002,0,0,1,1,0,10002);
insert into user_account values(13003,0,0,1,1,0,10003);

-- RESERVATION
-- ID, ENTRY_DATE, LEASE_TIME, STATUS, BOOK_ID, USER_ID
insert into reservation values(14001,sysdate(),15,'Reserved',11001,10001);
insert into reservation values(14002,sysdate(),15,'Reserved',11002,10002);
insert into reservation values(14003,sysdate(),15,'Reserved',11001,10003);





