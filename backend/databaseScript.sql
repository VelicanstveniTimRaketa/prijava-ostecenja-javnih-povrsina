insert into public.tipovi_ostecenja (naziv) values
                                                ('Oštećenje cestovne površine'),
                                                ('Oštećenje cijevi na javnoj površini'),
                                                ('Oštećenje elektrike nad cestom'),
                                                ('Oštećenje vodovoda ispod javne površine');

insert into public.gradski_uredi (ostecenje_id, naziv,active) values
                                                                  (1,'Gradski ured za ceste','true'),
                                                                  (2,'Gradski ured za vodovod','true'),
                                                                  (3,'Gradski ured za elektriku','true'),
                                                                  (4,'Gradski ured za cijevi','true');

insert into public.korisnici (ured_id,ured_status,email,username,ime,prezime,password,role) values
                                                                                                (NUll,'active','anon@anon.com','ANON','ANON','ANON','','ANON'),--dodavanje anonimnog korisnika
                                                                                                (NULL,'active','admin@admin.com','ADMIN','ADMIN','ADMIN','','ADMIN'),--dodavanje admin korisnika
                                                                                                ('1','active','guest@gmail.com', 'guest','josip','Sare','','USER'),
                                                                                                ('1','active','korisnik1@gmail.com', 'korisnik1','bigD','Najev','','USER'),
                                                                                                ('2','pending','korisnik2@gmail.com', 'korisnik2','smallD','Zoricic','','USER'),
                                                                                                (NULL,NULL,'korisnik3@gmail.com', 'korisnik3','Nikola','Boticelli','','USER'),
                                                                                                (NULL,NULL,'korisnik4@gmail.com', 'korisnik4','Ivan','Elezinjo','','USER');

insert into public.lokacije (lokacija_id, latitude, longitude) values
                                                                   (default, 45.81505, 15.98195),
                                                                   (default, 45.3271, 14.4422),
                                                                   (default, 45.5550, 18.6955),
                                                                   (default, 43.5147, 16.4435),
                                                                   (default, 45.4929, 15.5553),
                                                                   (default, 45.81509, 15.98190),
                                                                   (default, 45.81502, 15.98196);




insert into public.prijave
(gradski_ured_Id, opis, lokacija_id, kreator_id, prvo_vrijeme_prijave, parent_prijava_id, vrijeme_otklona) values
                                                                                                               (1,'', 1, 1, current_timestamp, null, null),
                                                                                                               (2,'', 3, 1, current_timestamp, null, null);
insert into public.prijave
(gradski_ured_Id, opis, lokacija_id, kreator_id, prvo_vrijeme_prijave, parent_prijava_id, vrijeme_otklona) values
                                                                                                               (2,'', 2, 2, current_timestamp, 1, null),
                                                                                                               (4,'', 6, 3, current_timestamp, 1, null),
                                                                                                               (1,'', 6, 2, current_timestamp - '10 hours' :: interval, null, current_timestamp),
                                                                                                               (2,'', 2, 4, current_timestamp - '1 day'::interval, 2, null),
                                                                                                               (4,'', 7, 1, current_timestamp - '2 hours' :: interval, null, null),
                                                                                                               (2,'', 7, 2, current_timestamp - '5 hours' :: interval, 2, null),
                                                                                                               (3,'', 4, 3, current_timestamp - '12 hours' :: interval, null, current_timestamp),
                                                                                                               (1,'', 2, 4, current_timestamp - '3 days' :: interval, 1, null),
                                                                                                               (2,'', 4, 4, current_timestamp - '7 days' :: interval, null, current_timestamp - '1 day' :: interval),
                                                                                                               (2,'', 3, 1, current_timestamp - '10 days' :: interval, 2 , null);


select * from korisnici;

select prijave.* from prijave natural join lokacije where latitude between (45.3271 - 0.001) and (45.3271 + 0.001) and longitude between (14.4422 - 0.001) and (14.4422 + 0.001);


select * from prijave;