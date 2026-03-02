import React from "react";
import { Link, NavLink } from "react-router-dom";
import Logo from "../phamacity.jpg"

const Sidebar = () => {
  return (
    <div>
      <aside className="navbar-aside" id="offcanvas_aside">
        <div className="aside-top">
          <Link to="/" className="brand-wrap">
            <img
              src={Logo}
              style={{ height: "46px" }}
              className="logo"
              alt="DrugStore Admin"
            />
          </Link>
          <div>
            <button className="btn btn-aside-minimize">
              <i className="fas fa-stream"></i>
            </button>
          </div>
        </div>

        <nav>
          <ul className="menu-aside">

            <li className="menu-section">Tổng quan</li>

            <li className="menu-item">
              <NavLink
                activeClassName="active"
                className="menu-link"
                to="/"
                exact={true}
              >
                <i className="icon fas fa-home"></i>
                <span className="text">Dashboard</span>
              </NavLink>
            </li>

            <li className="menu-section">Quản lý</li>

            <li className="menu-item">
              <NavLink
                activeClassName="active"
                className="menu-link"
                to="/products"
              >
                <i className="icon fas fa-capsules"></i>
                <span className="text">Sản phẩm</span>
              </NavLink>
            </li>
            <li className="menu-item">
              <NavLink
                activeClassName="active"
                className="menu-link"
                to="/addproduct"
              >
                <i className="icon fas fa-plus-circle"></i>
                <span className="text">Thêm sản phẩm</span>
              </NavLink>
            </li>
            <li className="menu-item">
              <NavLink
                activeClassName="active"
                className="menu-link"
                to="/category"
              >
                <i className="icon fas fa-th-list"></i>
                <span className="text">Danh mục</span>
              </NavLink>
            </li>
            <li className="menu-item">
              <NavLink
                activeClassName="active"
                className="menu-link"
                to="/orders"
              >
                <i className="icon fas fa-clipboard-list"></i>
                <span className="text">Đơn hàng</span>
              </NavLink>
            </li>

            <li className="menu-section">Hệ thống</li>

            <li className="menu-item">
              <NavLink
                activeClassName="active"
                className="menu-link"
                to="/users"
              >
                <i className="icon fas fa-users"></i>
                <span className="text">Người dùng</span>
              </NavLink>
            </li>

          </ul>
        </nav>
      </aside>
    </div>
  );
};

export default Sidebar;

