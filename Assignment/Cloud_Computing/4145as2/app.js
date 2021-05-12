const express = require('express');
const mysql546 = require('mysql2');
const app546 = express();
const Client = require('ssh2').Client;
const ssh546 = new Client();

app546.use(express.json());

const bodyParser546 = require('body-parser');
app546.use(bodyParser546.urlencoded({ extended: false }));
app546.use(bodyParser546.json());

//Sources:https://medium.com/@devontem/nodejs-express-using-ssh-to-access-mysql-remotely-60372832dd08
//learn how to remotely connect to FCS DB
//Create connection
const connect = new Promise(function (resolve, reject) {
    ssh546.on('ready', () => {

        console.log('SSH Connection :: READY');
        ssh546.forwardOut('127.0.0.1', 3306, 'db.cs.dal.ca', 3306, (err, stream) => {
            if (err) throw err;
            db546 = mysql546.createConnection({
                host: 'db.cs.dal.ca',
                user: 'xinyu',
                password: 'B00783546',
                port: '3306',
                database: 'xinyu',
                stream: stream
            });
            db546.connect((err) => {
                console.log("Mysql connected");
                if (err) {
                    throw err;
                }

            });
        });
    }).connect({
        host: 'bluenose.cs.dal.ca',
        username: 'xinyu',
        port: 22,
        readyTimeout: 99999,
        password: 'B00783546'
    });
}).catch((err) => { if (err) throw err; });

// const db546 = mysql546.createConnection({
//     host: 'db.cs.dal.ca',
//     user: 'xinyu',
//     password: process.env.password,
//     port: '3306',
//     database: 'xinyu',
//     stream: stream
// });
// //Connect
// db546.connect((err546) => {
//     if (err546) {
//         throw err546;
//     }
//     console.log("Mysql connected");
// });

// let db546 = mysql546.createConnection({
//     host: process.env.RDS_HOSTNAME || "a3dbtest.c4qd3xtvhgvo.us-east-1.rds.amazonaws.com",
//     user: process.env.RDS_USERNAME || 'admin',
//     password: process.env.RDS_PASSWORD || 'B00783546',
//     port: process.env.RDS_PORT || '3306'
// });

// db546.connect((err) => {
//     if (err) {
//         console.error('Database connection failed: ' + err.stack);
//         return;
//     }

//     console.log('Connected to database.');
// });

app546.get('/', (req546, res546) => {
    res546.send('Hello World from Express/Nodejs');
});

//get all the jobs
app546.get('/api546/jobs546', (req546, res546) => {
    let sql546 = 'SELECT * FROM jobs546'
    let query546 = db546.query(sql546, (err, jobs546) => {
        if (err) {
            throw err;
        }
        console.log(jobs546);
        res546.send(jobs546);
    });
});
//retrieve data correspoding to jobName and partId
app546.get('/api546/jobs546/:jobName/:partId', (req546, res546) => {
    let wherejob546 = 'jobName= ?';
    let wherepartid546 = 'partId= ?'
    let sql546 = 'SELECT * FROM jobs546 WHERE ' + wherejob546 + 'AND ' + wherepartid546;
    let values546 = [req546.params.jobName, req546.params.partId];

    console.log(sql546);
    console.log(values546);

    let query546 = db546.query(sql546, values546, (err, result546) => {
        if (err) {
            throw err;
        }
        console.log(result546);
        if (!result546)
            res546.status(404).send('Job with jobName: ' + req546.params.jobName + ' and PartId: ' + req546.params.partId + ' was not found');
        res546.send('Data retrieved with input jobName and partId');
    });
});

//create application/json parser
const jsonParser546 = bodyParser546.json();

//insert data
app546.post('/api546/jobs546', jsonParser546, (req546, res546) => {
    console.log('In Jobs post with JobName: ' + req546.body.jobName + ' PartId: ' + req546.body.partId + ' qty: ' + req546.body.qty);
    let wherejob546 = 'jobName= ?';
    let wherepartid546 = 'partId= ?'
    let sqlSelect546 = 'SELECT * FROM jobs546 WHERE ' + wherejob546 + 'AND ' + wherepartid546;
    let values546 = [req546.body.jobName, req546.body.partId];
    let sql546 = 'INSERT INTO jobs546 SET ?';
    let data546 = { jobName: req546.body.jobName, partId: req546.body.partId, qty: req546.body.qty };
    console.log(values546);
    console.log(data546);

    let querySelect546 = db546.query(sqlSelect546, values546, (err, result546) => {
        console.log(querySelect546);
        console.log(result546);
        if (result546 == "") {
            console.log('job not found and to be inserted/pushed');
            let query546 = db546.query(sql546, data546, (err, jobs546) => {
                if (err) {
                    throw err;
                }
                res546.send('Data {' + data546.jobName + ',' + data546.partId + ',' + data546.qty + '}inserted in the table');
                console.log(jobs546);
            });
        }
        else res546.status(404).send('The job with the given jobName' + req546.body.jobName + 'and partId' + req546.body.partId + 'exists already');
    });

});
//const jsonParser_546 = bodyParser546.json();
app546.put('/api546/jobs546', jsonParser546, (req546, res546) => {
    //jobName:string
    console.log(typeof (req546.body.jobName));
    //look up the job with given jobname and partid
    //if not existing, return 404
    console.log('In jobs update with jobname:' + req546.body.jobName + ' partId: ' + req546.body.partId + ' qty: ' + req546.body.qty);
    let wherejob546 = 'jobName= ?';
    let wherepartid546 = 'partId= ?';
    let values546 = [req546.body.jobName, parseInt(req546.body.partId)];
    //jobName:string
    console.log(typeof (req546.body.jobName));
    console.log(typeof (req546.body.partId));
    let sqlSelect546 = 'SELECT * FROM jobs546 WHERE ' + wherejob546 + ' AND  ' + wherepartid546;
    console.log(sqlSelect546);

    //let condition = ' jobName =' + `${str546}` + ' AND ' + 'partId=' + parseInt(req546.body.partId);

    //let sql546 = 'UPDATE jobs546 SET ?  WHERE ' + wherejob546 + ' AND  ' + wherepartid546;
    let sql546 = 'UPDATE jobs546 SET ? WHERE (' + `jobName` + ' ="' + req546.body.jobName + '")  AND (' + `partId` + ' = ' + parseInt(req546.body.partId) + ' )';
    let data546 = { qty: parseInt(req546.body.qty) };
    //let data546 = [parseInt(req546.body.qty), req546.body.jobName, parseInt(req546.body.partId)];
    console.log(sql546);
    console.log(data546);

    let querySelect546 = db546.query(sqlSelect546, values546, (err, result546) => {

        console.log(result546);

        if (result546 != "") {
            console.log('job found to be updated');
            let query546 = db546.query(sql546, data546, (err, jobs546) => {

                if (err) {
                    throw err;
                }
                res546.send('Data {' + req546.body.jobName + ',' + req546.body.partId + ',' + data546.qty + '}updated in table');
                console.log(jobs546);
            });
        }
        else res546.status(404).send('The job with the given jobName ' + req546.body.jobName + 'and partId' + req546.body.partId + ' was not found');
    });
});


const port546 = process.env.PORT || 3000;

app546.listen(port546, () => console.log(`Listening on port ${port546}`));

