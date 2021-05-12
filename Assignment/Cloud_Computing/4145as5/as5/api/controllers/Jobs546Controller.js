/**
 * Jobs546Controller
 *
 * @description :: Server-side actions for handling incoming requests.
 * @help        :: See https://sailsjs.com/docs/concepts/actions
 */


module.exports = {
  getJobs546: function (req, res) {
    Jobs546.find({}).exec((err, jobs546) => {
      if (err) {
        res.send(500, { error: 'Database Error' });
      }
      // res.view('\\pages\\viewData546', { jobs: jobs });
      res.send(jobs546);
    });
  },
  getJobs546ByID: function (req, res) {
    Jobs546.findOne({ jobName: req.params.jobName, partId: req.params.partId }).exec((err, job546) => {
      if (err) {
        res.send(500, { error: 'Cannot find such job' });
      }
      //res.view('\\pages\\viewData546', { job: job });
      res.send(job546);
    });
  },
  addJobs546: function (req, res) {
    let jobName = req.body.jobName;
    let partId = req.body.partId;
    let qty = req.body.qty;
    Jobs546.findOne({ jobName: req.params.jobName, partId: req.params.partId, qty: req.body.qty }).exec((job546) => {
      if (job546) {
        res.send(500, { error: 'Job Already Exist' });

      } else {
        Jobs546.create({ jobName: jobName, partId: partId, qty: qty }).exec((err) => {
          if (err) {
            res.send(500, { error: 'Database Error' });
          }
          res.send('Data {' + jobName + ',' + partId + ',' + qty + '}inserted in the table');
          res.redirect('\\pages\\viewData546');
        });
      }
    });
  },

  deleteJobs546: function (req, res) {
    Jobs546.destroy({ jobName: req.params.jobName, partId: req.params.partId }).exec((err) => {
      if (err) {
        res.send(500, { error: 'Database Error' });
      }

      res.redirect('\\pages\\viewData546');
    });

    return false;
  },
  // editJobs546: function (req, res, next) {
  //   Jobs546.findOne({ jobName: req.params.jobName, partId: req.params.partId }).exec((err, job546) => {
  //     if (err) {
  //       res.send(500, { error: 'Database Error' });
  //     }
  //     if (!job546) {
  //       return next();
  //     }
  //     res.view({ job546: job546 });
  //   });
  // },
  updateJobs546: function (req, res, next) {

    Jobs546.findOne({ jobName: req.params.jobName, partId: req.params.partId, qty: req.body.qty }).exec((job546) => {
      if (err) { return next(err); }
      if (!job546) {
        res.status(404).send('The job was not found');
        return next();
      } else {
        Jobs546.update({ jobName: req.params.jobName, partId: req.params.partId }, { qty: req.body.qty }).exec((err) => {
          if (err) {
            res.send(500, { error: 'Database Error' });
            return res.redirect('\\pages\\editData546');
          }
        });
      }
      res.redirect('\\pages\\viewData546');
    });

    return false;
  },
  viewData546: function (req, res) {
    Jobs546.find({}).exec((err, jobs546) => {
      if (err) {
        res.send(500, { error: 'Database Error' });
      }
      res.view('\\pages\\viewData546', { jobs546: jobs546 });
    });
  },

  addData546: function (req, res) {
    Jobs546.findOne({ jobName: req.params.jobName, partId: req.params.partId, qty: req.body.qty }).exec((job546) => {
      if (job546) {
        res.send(404, { error: 'Job Already Exist' });

      } else {
        Jobs546.create({ jobName: req.body.jobName, partId: req.body.partId, qty: req.body.qty }).exec((err) => {
          if (err) {
            res.send(500, { error: 'Database Error' });
            // return res.redirect('\\pages\\editData546');
          }
          res.redirect('/viewData546');
        });
      }
    });
  },
  editData546: function (req, res) {
    Jobs546.findOne({ jobName: req.params.jobName, partId: req.params.partId, qty: req.body.qty }).exec((err) => {
      if (err) {
        res.send(500, { error: 'Database Error, find issue' });

      }
      // if (!job546) {
      //   res.status(404).send('The job was not found');

      // }
      else {

        Jobs546.updateOne({ jobName: req.body.jobName, partId: req.body.partId }, { qty: req.body.qty }).exec((err) => {
          if (err) {
            res.send(500, { error: 'Database Error' });
            res.redirect('\\pages\\editData546');
          }

          res.redirect('/viewData546');
        });
      }
    });
  }
};
