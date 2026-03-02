import React, { useEffect } from "react";
import "./App.css";
import "./responsive.css";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
import axios from "axios";
import HomeScreen from "./screens/HomeScreen";
import SingleProduct from "./screens/SingleProduct";
import Login from "./screens/Login";
import Register from "./screens/Register";
import CartScreen from "./screens/CartScreen";
import ShippingScreen from "./screens/ShippingScreen";
import ProfileScreen from "./screens/ProfileScreen";
import PaymentScreen from "./screens/PaymentScreen";
import PlaceOrderScreen from "./screens/PlaceOrderScreen";
import OrderScreen from "./screens/OrderScreen";
import NotFound from "./screens/NotFound";
import PrivateRouter from "./PrivateRouter";
import PdfFileScreen from "./screens/PdfFileScreen";
import SinglePdf from "./screens/SinglePdf";
import PDF from "./components/PDF";
import SearchProduct from "./screens/SearchProduct";
import FavoriteScreen from "./screens/FavoriteScreen";

const POLL_INTERVAL = 30000; // 30 giây

const App = () => {
  const handleSessionExpired = (message) => {
    if (message.includes("another device")) {
      toast.warning(
        "⚠️ Tài khoản của bạn vừa đăng nhập ở nơi khác. Bạn sẽ bị đăng xuất sau 3 giây...",
        { autoClose: 3000, toastId: "session-expired" }
      );
      setTimeout(() => {
        localStorage.removeItem("userInfo");
        localStorage.removeItem("cartItems");
        window.location.href = "/login";
      }, 3000);
    } else {
      localStorage.removeItem("userInfo");
      window.location.href = "/login";
    }
  };

  useEffect(() => {
    const interceptor = axios.interceptors.response.use(
      (response) => response,
      (error) => {
        const status = error?.response?.status;
        const message = error?.response?.data?.message || "";
        const url = error?.config?.url || "";
        if (status === 401 && !url.includes("/api/users/login")) {
          handleSessionExpired(message);
        }
        return Promise.reject(error);
      }
    );
    return () => axios.interceptors.response.eject(interceptor);
  }, []);

  useEffect(() => {
    const poll = setInterval(() => {
      const userInfo = (() => { try { return JSON.parse(localStorage.getItem("userInfo")); } catch { return null; } })();
      if (!userInfo?.token) return;
      axios
        .get("/api/users/check-session", {
          headers: { Authorization: `Bearer ${userInfo.token}` },
        })
        .catch(() => {});
    }, POLL_INTERVAL);
    return () => clearInterval(poll);
  }, []);

  return (
    <Router>
      <ToastContainer
        position="top-right"
        autoClose={5000}
        hideProgressBar={false}
        newestOnTop={false}
        closeOnClick
        rtl={false}
        pauseOnFocusLoss
        draggable
        pauseOnHover
        theme="light"
      />
      <Switch>
        <Route path="/" component={HomeScreen} exact />
        <Route path="/search/:keyword" component={HomeScreen} exact />
        <Route path="/category/:item" component={HomeScreen} exact />
        <Route path="/search/:keyword" component={HomeScreen} exact />
        <Route path="/page/:pagenumber" component={HomeScreen} exact />
        <Route
          path="/search/:keyword/page/:pageNumber"
          component={HomeScreen}
          exact
        />
        <Route path="/products/:id" component={SingleProduct} />
        <PrivateRouter path="/search/:param" component={SearchProduct} />
        <Route path="/login" component={Login} />
        <Route path="/register" component={Register} />
        <PrivateRouter path="/profile" component={ProfileScreen} />
        <Route path="/cart/:id?" component={CartScreen} />
        <Route path="/favorite/:id?" component={FavoriteScreen} />
        <PrivateRouter path="/shipping" component={ShippingScreen} />
        <PrivateRouter path="/payment" component={PaymentScreen} />
        <PrivateRouter path="/placeorder" component={PlaceOrderScreen} />
        <PrivateRouter path="/order/:id" component={OrderScreen} />
        <Route path="*" component={NotFound} />
      </Switch>
    </Router>
  );
};

export default App;
