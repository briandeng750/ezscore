create database ezscore;
grant usage on *.* to 'ezscore'@'%' identified by 'ezscore';
grant all privileges on ezscore.* to 'ezscore'@'%';
flush privileges;
