/*
CSCI 4145 - Assignment1 - Part B
Yurunyun Wang, B00776875, May 10, 2020
*/
const express = require('express'); //load the express module
const app = express();
app.use(express.json());

let jobs875 = [
    { jobName875: 'j1875', partId875: 1875, qty875: 322 },
    { jobName875: 'j2875', partId875: 2875, qty875: 40 },
    { jobName875: 'j3875', partId875: 3875, qty875: 33 }
];

//case - GET welcome page
app.get('/', (req, res) => {
    res.send('Hello from Jobs and Parts server!!!');
});

//case - GET all jobs
app.get('/all', (req, res) => {
    res.send(jobs875);
});

//case - GET with jobId and partId
app.get('/:jobId875/:partId875', (req, res) => {
    const job = jobs875.find(c => c.partId875 === parseInt(req.params.partId875));
    if (!job) res.status(404).send('The job (' + req.params.jobName875 + ') with the partId ' + req.params.partId875 + ' was not found!');
    res.send(job);
});

//case - POST a new job into array if the job doesn't exist before
app.post('/jobs875', (req, res) => {
    console.log('In app.post with job name: ' + req.body.jobName875 + ' and partId: ' + req.body.partId875);
    const job = jobs875.find(c => c.partId875 === parseInt(req.body.partId875));
    console.log(req.body.partId875);
    if (!job) {
        console.log('The job not found and to be inserted to current length: ' + jobs875.length);
        const j = { jobName875: req.body.jobName875, partId875: req.body.partId875, qty875: req.body.qty875 };
        jobs875.push(j);
        console.log('Push success!');
        res.send(jobs875[jobs875.length - 1]);
    }
    else {
        console.log('The job (' + req.body.jobName875 + ') with the partId ' + req.body.partId875 + ' exists already!');
        res.status(404).send('The job (' + req.body.jobName875 + ') with the partId ' + req.body.partId875 + ' exists already!');
    }
})

//case - PUT update the qty when the jobName and partId matches
app.put('/jobs875', (req, res) => {
    console.log('In app.post with job name: ' + req.body.jobName875 + ' and partId: ' + req.body.partId875);
    const pId = parseInt(req.body.partId875);
    const job = jobs875.find(c => c.partId875 === pId);

    const jobIndex = jobs875.findIndex(({ partId875 }) => partId875 === pId);
    console.log('Found job index' + jobIndex);

    if (job) {
        job.qty875 = req.body.qty875;
        jobs875[jobIndex] = job;
        res.send(jobs875[jobIndex]);
    }
    else {
        console.log('The job (' + req.body.jobName875 + ') with the partId ' + req.body.partId875 + ' does not exists!');
        res.status(404).send('The job (' + req.body.jobName875 + ') with the partId ' + req.body.partId875 + ' does not exists!');
    }
})

//PORT
const port = process.env.PORT || 3001;
app.listen(port, () => console.log(`Listening on port ${port}...`));