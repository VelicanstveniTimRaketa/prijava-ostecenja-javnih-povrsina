insert into public.korisnici (id,email,username,ime,prezime,password_hash,role,token) values
                                                                                          (default, 'guest@gmail.com', 'guest','josip','Sare','','',''),
                                                                                          (default, 'korisnik1@gmail.com', 'korisnik1','bigD','Najev','','',''),
                                                                                          (default, 'korisnik2@gmail.com', 'korisnik2','smallD','Zoricic','','',''),
                                                                                          (default, 'korisnik3@gmail.com', 'korisnik3','Nikola','Boticelli','','',''),
                                                                                          (default, 'korisnik4@gmail.com', 'korisnik4','Ivan','Elezinjo','','','');

insert into public.lokacije (lokacija_id, latitude, longitude) values
                                                                   (default, 45.81505, 15.98195),
                                                                   (default, 45.3271, 14.4422),
                                                                   (default, 45.5550, 18.6955),
                                                                   (default, 43.5147, 16.4435),
                                                                   (default, 45.4929, 15.5553),
                                                                   (default, 45.81509, 15.98190),
                                                                   (default, 45.81502, 15.98196);

insert into public.gradski_uredi (id, naziv) values
                                                 (default, 'Vijece za ceste'),
                                                 (default, 'Vijece za vodovod'),
                                                 (default, 'Vijece za elektriku'),
                                                 (default, 'Vijece za cijevi');

insert into public.tipovi_ostecenja (id, ured_id, naziv) values
                                                             (default, 1, 'Oštećenje cestovne površine'),
                                                             (default, 2, 'Oštećenje cijevi na javnoj površini'),
                                                             (default, 3, 'Oštećenje elektrike nad cestom'),
                                                             (default, 4, 'Oštećenje vodovoda ispod javne površine');

insert into public.prijave
(id, ostecenje_id,opis, lokacija_id, kreator_id, prvo_vrijeme_prijave, parent_prijava_id, vrijeme_otklona) values
                                                                                                               (default, 1,'', 1, 1, current_timestamp, null, null),
                                                                                                               (default, 2,'', 3, 1, current_timestamp, 1, null),
                                                                                                               (default, 2,'', 2, 2, current_timestamp, null, null),
                                                                                                               (default, 3,'', 6, 3, current_timestamp, 1, null),
                                                                                                               (default, 2,'', 6, 2, current_timestamp - '10 hours' :: interval, null, current_timestamp),
                                                                                                               (default, 2,'',2,4,current_timestamp - '1 day'::interval,2,null),
                                                                                                               (default, 3,'', 7, 1, current_timestamp - '2 hours' :: interval, null, null),
                                                                                                               (default, 4,'', 7, 2, current_timestamp - '5 hours' :: interval, 2, null),
                                                                                                               (default, 4,'', 4, 3, current_timestamp - '12 hours' :: interval, null, current_timestamp),
                                                                                                               (default, 3,'', 2, 4, current_timestamp - '3 days' :: interval, 1, null),
                                                                                                               (default, 4,'', 4, 4, current_timestamp - '7 days' :: interval, null, current_timestamp - '1 day' :: interval),
                                                                                                               (default, 3,'', 3, 1, current_timestamp - '10 days' :: interval, 2 , null);


select * from korisnici;
select prijave.* from prijave natural join lokacije where latitude between (45.3271 - 0.0005) and (45.3271 + 0.0005) and longitude between (14.4422 - 0.0005) and (14.4422 + 0.0005);
