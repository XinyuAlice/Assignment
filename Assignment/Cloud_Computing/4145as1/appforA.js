const http546 = require('http');

const server546 = http546.createServer((req546, res546) => {
  console.log('Processing request');
  if (req546.url === '/') {
    res546.write('Hello from Job Server');
    res546.end();
  }
  if (req546.url === '/api546/jobs546') {
    res546.write(JSON.stringify(jobs546));
    res546.end();
  }

  if (req546.url === '/api546/jobs546/Job1546/3546') {
    console.log(req546.url);
    param546 = req546.url;
    param_array546 = param546.split('/');

    partid546 = parseInt(param_array546.pop());
    jobname546 = param_array546.pop();
    console.log(jobname546);
    console.log(partid546);
    for (i = 0; i < jobs546.length; i++) {
      job546 = jobs546[i];
      if (job546.jobName === jobname546 && job546.partId === parseInt(partid546)) {
        res546.write(JSON.stringify(job546));
        res546.end();
      }
    }
  }
});

let jobs546 = [
  { jobName: 'Job1546', partId: 3546, qty: 55 },
  { jobName: 'Job1546', partId: 4546, qty: 35 },
  { jobName: 'Job2546', partId: 4546, qty: 33 },
  { jobName: 'Job3546', partId: 5546, qty: 46 },
];

server546.listen(3000);

console.log('Listening on port 3000...');
