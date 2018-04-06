blood-donation
==============

Server for Blood Donation app.

Config
======

All the config <key, value> pairs can be found in <u>Resources>config.properties</u>.

The <b>db</b> properties are for the production/debug mode. The <b>testdb</b> properties will be automatically triggered
when running unit tests. You can force database recreation by specifying <b>db.creation=true</b> and having a valid SQL 
script in the <b>db.creation_sql</b> key. Tests automatically recreate the database from the <b>db.creation_sql</b> script
(so make sure that's valid before testing!)

For some reason, <b>db.creation</b> is shown as <b>true</b> after recreating the database, but its actual value is <b>false</b>.