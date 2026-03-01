import React, { useEffect, useState } from "react";
import { Link, useHistory } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import { toast } from "react-toastify";
import { USER_CREATE_RESET } from "../../Redux/Constants/UserContants";
import { createUser } from "../../Redux/Actions/userActions";
import Toast from "../LoadingError/Toast";
import Message from "../LoadingError/Error";
import Loading from "../LoadingError/Loading";

const ToastObjects = {
  pauseOnFocusLoss: false,
  draggable: false,
  pauseOnHover: false,
  autoClose: 2000,
};
const AddUserMain = () => {
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const history = useHistory();

  const dispatch = useDispatch();

  const userCreate = useSelector((state) => state.userCreate);
  const { loading, error, success } = userCreate;

  useEffect(() => {
    if (success) {
      toast.success("Đã thêm người dùng thành công!", ToastObjects);
      dispatch({ type: USER_CREATE_RESET });
      history.push("/users");
    }
  }, [success, dispatch, history]);

  const submitHandler = (e) => {
    e.preventDefault();
    dispatch(createUser(name, email, password));
  };

  return (
    <>
      <Toast />
      <section className="content-main" style={{ maxWidth: "1200px" }}>
        <form onSubmit={submitHandler}>
          <div className="content-header justify-content-start gap-3">
            <Link to="/users" className="btn btn-danger text-white">
              Đi đến danh sách người dùng
            </Link>
            <button type="submit" className="btn btn-primary">
              Thêm người dùng
            </button>
          </div>

          <div className="row mb-4">
            <div className="col-xl-8 col-lg-8">
              <div className="card mb-4 shadow-sm">
                <div className="card-body">
                  {error && <Message variant="alert-danger">{error}</Message>}
                  {loading && <Loading />}
                  <div className="mb-4">
                    <label htmlFor="user_title" className="form-label">
                      Tên người dùng
                    </label>
                    <input
                      type="text"
                      placeholder="Nhập tên người dùng..."
                      className="form-control"
                      id="user_title"
                      required
                      value={name}
                      onChange={(e) => setName(e.target.value)}
                    />
                  </div>
                  <div className="mb-4">
                    <label htmlFor="user_email" className="form-label">
                      Số điện thoại
                    </label>
                    <input
                      type="number"
                      placeholder="Nhập Sdt...."
                      className="form-control"
                      id="user_email"
                      required
                      value={email}
                      onChange={(e) => setEmail(e.target.value)}
                    />
                  </div>
                  <div className="mb-4">
                    <label htmlFor="user_password" className="form-label">
                      password
                    </label>
                    <input
                      type="text"
                      placeholder="Nhập Password...."
                      className="form-control"
                      id="user_password"
                      required
                      value={password}
                      onChange={(e) => setPassword(e.target.value)}
                    />
                  </div>
                  </div>
                </div>
            </div>
          </div>
        </form>
      </section>
    </>
  );
};

export default AddUserMain;
