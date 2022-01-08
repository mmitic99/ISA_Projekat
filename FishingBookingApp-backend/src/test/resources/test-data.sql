
insert into ROLE (role_name) values ('ROLE_USER'); -- id = 1
insert into ROLE (role_name) values ('ROLE_cottageOwner'); -- id = 2
insert into ROLE (role_name) values ('ROLE_boatOwner'); -- id = 3
insert into ROLE (role_name) values ('ROLE_ADMIN'); -- id = 4

insert into address (city, country, latitude, longitude, number, postal_code, street)
values ('Beograd', 'Srbija', 0, 0, '11', '21100', 'Glavna ulica'); -- id = 1
insert into address (city, country, latitude, longitude, number, postal_code, street)
values ('Novi Sad', 'Srbija', 0, 0, '16', '23330', 'Zabacena ulica'); -- id = 2
insert into address (city, country, latitude, longitude, number, postal_code, street)
values ('Zlatibor', 'Srbija', 0, 0, 'bb', '28870', 'Periceva ulica'); -- id = 3

insert into users (enabled, user_mail_address, user_mobile_number, user_name, user_password, user_surname, verified, user_address_id, user_role)
values (false, 'isaproject.tim27+1@gmai.com', '+38160987789', 'Pero', '123', 'Peric', false, 1, 1); -- obican korisnik
insert into users (enabled, user_mail_address, user_mobile_number, user_name, user_password, user_surname, verified, user_address_id, user_role)
values (true, 'isaproject.tim27+2@gmai.com', '+38160111111', 'Filip', '123', 'Mikic', true, 2, 4);  -- admin
insert into users (enabled, user_mail_address, user_mobile_number, user_name, user_password, user_surname, verified, user_address_id, user_role)
values (false, 'isaproject.tim27+3@gmai.com', '+3816042134', 'Milica', '123', 'Miletic', true, 3, 1); -- obican korisnik
insert into users (enabled, user_mail_address, user_mobile_number, user_name, user_password, user_surname, verified, user_address_id, user_role)
values (false, 'isaproject.tim27+4@gmai.com', '+3816055555', 'Damir', '123', 'Dakic', false, 1, 2); -- cottageOwner
insert into users (enabled, user_mail_address, user_mobile_number, user_name, user_password, user_surname, verified, user_address_id, user_role)
values (false, 'isaproject.tim27+5@gmai.com', '+3816077777', 'Lazar', '123', 'Lazic', true, 2, 2); -- cottageOwner
insert into users (enabled, user_mail_address, user_mobile_number, user_name, user_password, user_surname, verified, user_address_id, user_role)
values (false, 'isaproject.tim27+6@gmai.com', '+3816099999', 'Bojan', '123', 'Bokic', true, 2, 3); -- boatOwner

insert into reservation_entities(deleted, name, price, promotional_description, rules_of_conduct, type, address_id)
values (false, 'neko ime', 2000, 'opis', 'pravila neka', 'cottage', 1)
