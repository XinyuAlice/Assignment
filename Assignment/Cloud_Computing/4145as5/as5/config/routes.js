/**
 * Route Mappings
 * (sails.config.routes)
 *
 * Your routes tell Sails what to do each time it receives a request.
 *
 * For more information on configuring custom routes, check out:
 * https://sailsjs.com/anatomy/config/routes-js
 */

module.exports.routes = {

  /***************************************************************************
  *                                                                          *
  * Make the view located at `views/homepage.ejs` your home page.            *
  *                                                                          *
  * (Alternatively, remove this and add an `index.html` file in your         *
  * `assets` directory)                                                      *
  *                                                                          *
  ***************************************************************************/

  '/': { view: 'pages/homepage' },
  'GET /getJobs546': { controller: 'Jobs546', action: 'getJobs546' },
  'GET /getJobs546ByID/:jobName/:partId': {
    controller: 'Jobs546', action: 'getJobs546ByID'
  },
  'POST /addJobs546': { controller: 'Jobs546', action: 'addJobs546' },
  //'PUT /editJobs546': { controller: 'Jobs546', action: 'editJobs546' },
  'PUT /updateJobs546': { controller: 'Jobs546', action: 'updateJobs546' },
  //'GET /viewData546': { controller: 'Jobs', action: 'viewData546' },
  'GET /viewData546': { controller: 'Jobs546', action: 'viewData546' },
  'GET /addData546': { view: 'pages/addData546' },

  // 'POST /addData546': { controller: 'Jobs', action: 'addData546' }
  'POST /addData546': { controller: 'Jobs546', action: 'addData546' },
  'GET /editData546': { view: 'pages/editData546' },
  //'GET /editData546': { controller: 'Jobs546', action: 'editData546' },
  //'PUT /editData546': { controller: 'Jobs546', action: 'editData546' },
  'POST /editData546': { controller: 'Jobs546', action: 'editData546' },
  'DELETE /deleteJobs546': { controller: 'Jobs546', action: 'deleteJobs546' }
  /***************************************************************************
    *                                                                          *
    * More custom routes here...                                               *
    * (See https://sailsjs.com/config/routes for examples.)                    *
    *                                                                          *
    * If a request to a URL doesn't match any of the routes in this file, it   *
    * is matched against "shadow routes" (e.g. blueprint routes).  If it does  *
    * not match any of those, it is matched against static assets.             *
    *                                                                          *
    ***************************************************************************/


};
