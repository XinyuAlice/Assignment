const express = require('express');
const app546 = express();

app546.use(express.json());

let jobs546 = [
  { jobName: 'Job1546', partId: 3546, qty: 55 },
  { jobName: 'Job1546', partId: 4546, qty: 35 },
  { jobName: 'Job2546', partId: 4546, qty: 33 },
  { jobName: 'Job3546', partId: 5546, qty: 46 },
];

app546.get('/', (req546, res546) => {
  res546.send('Hello World from Express/Nodejs');
});

//get all the jobs
app546.get('/api546/jobs546', (req546, res546) => {
  res546.send(jobs546);
});

app546.get('/api546/jobs546/:jobName/:partId', (req546, res546) => {
  console.log(jobs546);
  const job546 = jobs546.find((c) => (c.jobName === (req546.params.jobName) && (c.partId === parseInt(req546.params.partId))));
  //404
  if (!job546)
    return res546.status(404).send('The job with the given jobName and partId was not found');
  res546.send(job546);
});

app546.post('/api546/jobs546', (req546, res546) => {
  console.log('In jobs post with jobname:' + req546.body.jobName + ' partid:' + req546.body.partId + ' qty: ' + req546.body.qty);
  const job546 = jobs546.find(c => (c.jobName === (req546.body.jobName) && (c.partId === parseInt(req546.body.partId))));
  if (!job546) {
    console.log('Job not found and to be inserted/pushed -current lenght:' + jobs546.length);
    const j546 = {
      jobName: req546.body.jobName,
      partId: req546.body.partId,
      qty: req546.body.qty
    };
    jobs546.push(j546);
    console.log('jobs length:' + jobs546.length);
    console.log(jobs546);
    res546.send(jobs546[jobs546.length - 1]);
  }
  else {
    console.log('Job with jobName' + req546.body.jobName + 'and PartId' + req546.body.partId + 'exists already');
    res546.status(404).send('The job with the given jobName' + req546.body.jobName + 'and partId' + req546.body.partId + 'exists already');
  }
});

///:jobName/:partId
app546.put('/api546/jobs546', (req546, res546) => {

  //look up the job with given jobname and partid
  //if not existing, return 404
  console.log('In jobs update with jobname:' + req546.body.jobName + ' partid:' + req546.body.partId + ' qty: ' + req546.body.qty);
  const partId546 = parseInt(req546.body.partId);
  const jobname546 = req546.body.jobName;
  const job546 = jobs546.find(c => (c.partId === partId546) && (c.jobName === jobname546));
  console.log('Found job in Array -JobName:' + job546.jobName + 'and partId:' + job546.partId + ' and qty:' + job546.qty);
  const jobsIndex546 = jobs546.findIndex(({ jobName, partId }) => jobName === jobname546 && partId === partId546);
  console.log('Found job index: ' + jobsIndex546 + ' Job Name:' + job546.jobName + ' partid:' + job546.partId + ' qty:' + job546.qty);
  //404
  if (!job546) {
    console.log('Job with jobName ' + req546.body.jobName + 'and partId' + req546.body.partId + ' does not exist');
    return res546.status(404).send('The job with the given jobName and partId was not found');
  }


  else {
    //update the job
    job546.qty = req546.body.qty;
    console.log('Update Jobs element with index:' + jobsIndex546 + ' to job with jobName' + job546.jobName + ' and partId' + job546.partId + ' to new qty:' + job546.qty);
    console.log('Updated Job with ' + job546.jobName + ' partId: ' + job546.partId);
    jobs546[jobsIndex546] = job546;
    //return the updated job
    res546.send(jobs546[jobsIndex546])
  }



});


const port = process.env.PORT || 3000;

app546.listen(port, () => console.log(`Listening on port ${port}`));

