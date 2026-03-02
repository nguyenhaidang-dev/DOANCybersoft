import React from "react";
import Message from "../LoadingError/Error";
import Loading from "../LoadingError/Loading";
import Orders from "./Orders";
import { useState, useEffect } from "react";
import axios from "axios";
import { URL } from "../../Redux/Url";

const OrderMain = () => {
  const [isSearch, setIsSearch] = useState(0);
  const [search, setSearch] = useState("");
  const [search1, setSearch1] = useState("");
  const [search2, setSearch2] = useState("");
  const [data, setData] = useState([]);
  const [allOrders, setAllOrders] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchAllOrders = async () => {
      try {
        setLoading(true);
        const res = await axios.get(`${URL}/api/orders/all`);
        setAllOrders(res.data.data || []);
      } catch (err) {
        setError("Không thể tải danh sách đơn hàng");
      } finally {
        setLoading(false);
      }
    };
    fetchAllOrders();
  }, []);

  useEffect(() => {
    const fetchFiltered = async () => {
      if (isSearch === 1 && search) {
        try {
          const res = await axios.get(`${URL}/api/orders/search/${search}`);
          setData(res.data.data || []);
        } catch (err) { setData([]); }
      }
      if (isSearch === 2 && search1 && search1 !== "default") {
        try {
          const res = await axios.get(`${URL}/api/orders/status/${search1}`);
          setData(res.data.data || []);
        } catch (err) { setData([]); }
      }
      if (isSearch === 2 && (!search1 || search1 === "default")) {
        setData([]);
        setIsSearch(0);
      }
      if (isSearch === 3 && search2) {
        try {
          const res = await axios.get(`${URL}/api/orders/option/${search2}`);
          setData(res.data.data || []);
        } catch (err) { setData([]); }
      }
    };
    fetchFiltered();
  }, [search, search1, search2, isSearch]);

  const handleSearch = (e) => {
    setSearch(e.target.value);
    setIsSearch(1);
  };

  const handleChangeStatus = (e) => {
    setSearch1(e.target.value);
    setIsSearch(2);
  };

  const handleChangOption = (e) => {
    setSearch2(e.target.value);
    setIsSearch(3);
  };

  return (
    <section className="content-main">
      <div className="content-header">
        <h2 className="content-title">Danh sách đơn hàng</h2>
      </div>

      <div className="card mb-4 shadow-sm">
        <header className="card-header bg-white">
          <div className="row gx-3 py-3">
            <div className="col-lg-4 col-md-6 me-auto">
              <input
                type="number"
                placeholder="Nhập sdt..."
                className="form-control p-2"
                value={search}
                onChange={(e) => handleSearch(e)}
              />
            </div>
            <div className="col-lg-2 col-6 col-md-3">
              <select
                className="form-select"
                onChange={(e) => handleChangeStatus(e)}
              >
                <option value="default">Trạng thái</option>
                <option value="choxuli">Chờ xử lí</option>
                <option value="dahoanthanh">Đã hoàn thành</option>
              </select>
            </div>

            <div className="col-lg-2 col-6 col-md-3"></div>
          </div>
        </header>
        <div className="card-body">
          <div className="table-responsive">
            {loading ? (
              <Loading />
            ) : error ? (
              <Message variant="alert-danger">{error}</Message>
            ) : (
              <Orders orders={isSearch !== 0 ? data : allOrders} />
            )}
          </div>
        </div>
      </div>
    </section>
  );
};

export default OrderMain;
