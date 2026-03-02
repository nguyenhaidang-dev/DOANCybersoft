import React, { useEffect } from "react";
import { Link } from "react-router-dom";
import $ from "jquery";
import { useDispatch, useSelector } from "react-redux";
import { logout } from "../Redux/Actions/userActions";

const Header = () => {
  const dispatch = useDispatch();
  const { userInfo } = useSelector((state) => state.userLogin);

  useEffect(() => {
    $("[data-trigger]").on("click", function (e) {
      e.preventDefault();
      e.stopPropagation();
      var offcanvas_id = $(this).attr("data-trigger");
      $(offcanvas_id).toggleClass("show");
    });

    $(".btn-aside-minimize").on("click", function () {
      if (window.innerWidth < 768) {
        $("body").removeClass("aside-mini");
        $(".navbar-aside").removeClass("show");
      } else {
        $("body").toggleClass("aside-mini");
      }
    });
  }, []);

  const logoutHandler = () => {
    dispatch(logout());
  };

  return (
    <header className="main-header navbar">
      <div className="col-search">
        <button
          className="btn btn-icon btn-mobile me-2"
          data-trigger="#offcanvas_aside"
          style={{ display: "none" }}
        >
          <i className="fas fa-bars"></i>
        </button>
      </div>
      <div className="col-nav ms-auto">
        <ul className="nav">
          <li className="nav-item d-none d-md-flex align-items-center me-2">
            <span style={{ fontSize: "0.82rem", color: "#718096", fontWeight: 500 }}>
              <i className="fas fa-user-shield me-1" style={{ color: "#4fa607" }}></i>
              {userInfo?.name || "Admin"}
            </span>
          </li>
          <li className="nav-item">
            <Link className="nav-link btn-icon" title="Thông báo" to="#">
              <i className="fas fa-bell"></i>
            </Link>
          </li>
          <li className="dropdown nav-item">
            <Link
              className="dropdown-toggle ps-2 pe-1 d-flex align-items-center gap-2"
              data-bs-toggle="dropdown"
              to="#"
              style={{ textDecoration: "none" }}
            >
              <img
                className="img-xs rounded-circle"
                src="/images/favicon.png"
                alt="User"
              />
              <i className="fas fa-chevron-down" style={{ fontSize: "0.65rem", color: "#a0aec0" }}></i>
            </Link>
            <div className="dropdown-menu dropdown-menu-end" style={{ minWidth: "160px" }}>
              <div className="px-3 py-2 mb-1" style={{ borderBottom: "1px solid #e2e8f0" }}>
                <div style={{ fontSize: "0.82rem", fontWeight: 600, color: "#1a202c" }}>
                  {userInfo?.name || "Admin"}
                </div>
                <div style={{ fontSize: "0.75rem", color: "#718096" }}>Quản trị viên</div>
              </div>
              <Link
                onClick={logoutHandler}
                className="dropdown-item text-danger"
                to="#"
              >
                <i className="fas fa-sign-out-alt me-2"></i>Thoát
              </Link>
            </div>
          </li>
        </ul>
      </div>
    </header>
  );
};

export default Header;

