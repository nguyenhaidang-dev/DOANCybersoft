import React from "react";

const ContactInfo = () => {
  return (
    <div className="contactInfo container">
      <div className="row">
        <div className="col-12 col-md-4 contact-Box">
          <div className="box-info">
            <div className="info-image">
              <i className="fas fa-phone-alt"></i>
            </div>
            <h5>Facebook</h5>
            <a href="">
              <p>Phamacity Shop</p>
            </a>
          </div>
        </div>
        <div className="col-12 col-md-4 contact-Box">
          <div className="box-info">
            <div className="info-image">
              <i className="fas fa-map-marker-alt"></i>
            </div>
            <h5>Vị trí</h5>
            <a target="_blank" href="https://www.google.com/maps/search/173+Ph%E1%BA%A1m+Ng%C5%A9+L%C3%A3o,+Ph%C6%B0%E1%BB%9Dng+Ph%E1%BA%A1m+Ng%C5%A9+L%C3%A3o,+Qu%E1%BA%ADn+1,+Th%C3%A0nh+ph%E1%BB%91+H%E1%BB%93+Ch%C3%AD+Minh+v%E1%BB%81+%C4%91%E1%BB%8Ba+ch%E1%BB%89+%E1%BB%9F+HCM+%C4%91i+a/@10.7687967,106.6916242,17z/data=!3m1!4b1">
              <p className="location">173 Phạm Ngũ Lão, Phường Phạm Ngũ Lão, Quận 1, Thành phố Hồ Chí Minh</p>
            </a>
          </div>
        </div>
        <div className="col-12 col-md-4 contact-Box">
          <div className="box-info">
            <div className="info-image">
              <i className="fas fa-fax"></i>
            </div>
            <h5>Zalo</h5>
            <a href="https://zalo.me/012345678">
              <p>0123 206 636</p>
            </a>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ContactInfo;
