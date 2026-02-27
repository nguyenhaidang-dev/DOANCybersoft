import React from "react";

const CalltoActionSection = () => {
  const handleSubmit = (e) => {
    e.preventDefault();
  };

  return (
    <div className="subscribe-section">
      <div className="container">
        <div className="row">
          <div className="col-12">
            <div className="subscribe-head">
              <h2>Bạn cần tư vấn sức khoẻ?</h2>
              <p>
                Đăng ký nhận tin để nhận tư vấn miễn phí từ dược sĩ và
                cập nhật khuyến mãi mới nhất mỗi tuần.
              </p>
              <form className="form-section" onSubmit={handleSubmit}>
                <input
                  placeholder="Nhập địa chỉ Email của bạn..."
                  name="email"
                  type="email"
                  required
                />
                <input value="Đăng ký" name="subscribe" type="submit" />
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default CalltoActionSection;
