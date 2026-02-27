import React from "react";

const Footer = () => {
  return (
    <div className="footer">
      <div className="container">
        {/* Brand */}
        <div className="footer-content">
          <div className="footer-brand" style={{ textAlign: "center" }}>
            <h3>DrugStore</h3>
            <p>Nhà thuốc tin cậy — Chăm sóc sức khỏe của bạn</p>
          </div>
        </div>

        {/* Payment methods */}
        <div className="footer-bottom">
          <span style={{ color: "rgba(255,255,255,0.6)", fontSize: "13px", fontFamily: "'Nunito Sans', sans-serif" }}>
            Thanh toán an toàn:
          </span>
          <div className="card-name">
            <img alt="mastercard" src="https://upload.wikimedia.org/wikipedia/commons/thumb/b/b7/MasterCard_Logo.svg/200px-MasterCard_Logo.svg.png" />
          </div>
          <div className="card-name">
            <img alt="visa" src="https://upload.wikimedia.org/wikipedia/commons/2/2a/Mastercard-logo.svg" />
          </div>
          <div className="card-name">
            <img alt="paypal" src="https://upload.wikimedia.org/wikipedia/commons/thumb/b/b5/PayPal.svg/200px-PayPal.svg.png" />
          </div>
          <div className="card-name">
            <img alt="amex" src="https://upload.wikimedia.org/wikipedia/commons/thumb/3/30/American_Express_logo.svg/200px-American_Express_logo.svg.png" />
          </div>
        </div>

        {/* Copyright */}
        <p style={{ textAlign: "center", marginTop: "20px", color: "rgba(255,255,255,0.45)", fontSize: "12px", fontFamily: "'Nunito Sans', sans-serif" }}>
          &copy; {new Date().getFullYear()} DrugStore. All rights reserved.
        </p>
      </div>
    </div>
  );
};

export default Footer;
