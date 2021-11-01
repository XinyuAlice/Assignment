import React from 'react';
import { NavLink } from 'react-router-dom';
export default function NavMenu() {
  return (
    <div>
      <ul>
        <li>
          <NavLink to="/">
            Home
          </NavLink></li>
        <li><NavLink to="/">
          About
        </NavLink></li>
        <li><NavLink to="/">
          Project
        </NavLink></li>
        <li><NavLink to="/">
          Contact
        </NavLink></li>
      </ul>
    </div>
  )
}