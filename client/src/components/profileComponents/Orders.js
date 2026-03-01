import moment from "moment";
import React from "react";
import { Link, useHistory } from "react-router-dom";
import Message from "../LoadingError/Error";
import Loading from "../LoadingError/Loading";
import axios from "axios";
import { toast } from "react-toastify";
import { useDispatch } from "react-redux";
import { addToCart } from "../../Redux/Actions/cartActions";

const Orders = (props) => {
  const { loading, error, orders } = props;
  const history = useHistory();
  const dispatch = useDispatch();

  const handleReorder = async (orderId) => {
    try {
      const userInfo = JSON.parse(localStorage.getItem("userInfo"));
      const config = { headers: { Authorization: `Bearer ${userInfo?.token}` } };
      const res = await axios.get(`/api/orders/${orderId}`, config);
      const order = res.data?.data || res.data;

      const items = order.orderItems || [];
      if (items.length === 0) {
        toast.error("Không tìm thấy sản phẩm trong đơn hàng");
        return;
      }

      localStorage.removeItem("cartItems");

      for (const item of items) {
        await dispatch(addToCart(item.product?.id || item.productId, item.qty, "buy"));
      }

      toast.success("Đã thêm sản phẩm vào giỏ hàng!");
      history.push("/cart");
    } catch (err) {
      toast.error("Không thể đặt lại đơn hàng");
    }
  };

  return (
    <div className=" d-flex justify-content-center align-items-center flex-column">
      {loading ? (
        <Loading />
      ) : error ? (
        <Message variant="alert-danger">{error}</Message>
      ) : (
        <>
          {orders.length === 0 ? (
            <div className="col-12 alert alert-info text-center mt-3">
              Không có đơn hàng....
              <Link
                className="btn btn-success mx-2 px-3 py-2"
                to="/"
                style={{
                  fontSize: "12px",
                }}
              >
                Mua sắm ngay
              </Link>
            </div>
          ) : (
            <div className="table-responsive">
              <table className="table">
                <thead>
                  <tr>
                    <th>Mã đơn hàng</th>
                    <th>Trạng thái</th>
                    <th>Thời gian</th>
                    <th>Đơn giá</th>
                    <th>Hành động</th>
                  </tr>
                </thead>
                <tbody>
                  {orders.map((order) => (
                    <tr
                      className={`${
                        order.isPaid ? "alert-success" : "alert-danger"
                      }`}
                      key={order.id}
                    >
                      <td>
                        <a href={`/order/${order.id}`} className="link">
                          #{order.id}
                        </a>
                      </td>
                      <td>{order.isPaid ? <>Đã thanh toán</> : <>Chưa thanh toán</>}</td>
                      <td>
                        {order.isPaid
                          ? moment(order.paidAt).calendar()
                          : moment(order.createdAt).calendar()}
                      </td>
                      <td>{order.totalPrice}.000 vnđ</td>
                      <td style={{ display: "flex", gap: "6px", alignItems: "center" }}>
                        <a href={`/order/${order.id}`} className="btn btn-outline-secondary btn-sm">Xem chi tiết</a>
                        {order.isDelivered && (
                          <div className="btn btn-primary btn-sm" onClick={() => handleReorder(order.id)}>Đặt lại</div>
                        )}
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          )}
        </>
      )}
    </div>
  );
};

export default Orders;
