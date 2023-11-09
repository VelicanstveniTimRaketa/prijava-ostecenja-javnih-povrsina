insert into public.korisnici values
                                 (1, 'guest@gmail.com', 'guest'),
                                 (2, 'korisnik1@gmail.com', 'korisnik1'),
                                 (3, 'korisnik2@gmail.com', 'korisnik2'),
                                 (4, 'korisnik3@gmail.com', 'korisnik3'),
                                 (5, 'korisnik4@gmail.com', 'korisnik4');

insert into public.lokacije (id, latitude, longitude) values
                                                          (1, 45.81505, 15.98195),
                                                          (2, 45.3271, 14.4422),
                                                          (3, 45.5550, 18.6955),
                                                          (4, 43.5147, 16.4435),
                                                          (5, 45.4929, 15.5553),
                                                          (6, 45.81509, 15.98190),
                                                          (7, 45.81502, 15.98196);

insert into public.vijeca (id, naziv) values
                                          (1, 'Vijece za ceste'),
                                          (2, 'Vijece za vodovod'),
                                          (3, 'Vijece za elektriku'),
                                          (4, 'Vijece za cijevi');

insert into public.tipovi_ostecenja (id, vijece_id, naziv) values
                                                               (1, 1, 'Oštećenje cestovne površine'),
                                                               (2, 2, 'Oštećenje cijevi na javnoj površini'),
                                                               (3, 3, 'Oštećenje elektrike nad cestom'),
                                                               (4, 4, 'Oštećenje vodovoda ispod javne površine');

insert into public.prijave
(id, ostecenje_id,opis, lokacija_id, kreator_id, prvo_vrijeme_prijave, parent_prijava_id, vrijeme_otklona) values
                                                                                                               (1, 1,'', 1, 1, current_timestamp, null, null),
                                                                                                               (2, 2,'', 3, 1, current_timestamp, 1, null),
                                                                                                               (3, 2,'', 2, 2, current_timestamp, null, null),
                                                                                                               (4, 3,'', 6, 3, current_timestamp, 1, null),
                                                                                                               (5, 2,'', 6, 2, current_timestamp - '10 hours' :: interval, null, current_timestamp),
                                                                                                               (6,2,'',2,4,current_timestamp - '1 day'::interval,2,null),
                                                                                                               (7, 3,'', 7, 1, current_timestamp - '2 hours' :: interval, null, null),
                                                                                                               (8, 4,'', 7, 2, current_timestamp - '5 hours' :: interval, 2, null),
                                                                                                               (9, 4,'', 4, 3, current_timestamp - '12 hours' :: interval, null, current_timestamp),
                                                                                                               (10, 3,'', 2, 4, current_timestamp - '3 days' :: interval, 1, null),
                                                                                                               (11, 4,'', 4, 4, current_timestamp - '7 days' :: interval, null, current_timestamp - '1 day' :: interval),
                                                                                                               (12, 3,'', 3, 1, current_timestamp - '10 days' :: interval, 2 , null);


select * from prijave