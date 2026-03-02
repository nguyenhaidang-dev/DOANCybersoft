import axios from "axios";
import {
  CART_ADD_ITEM,
  CART_REMOVE_ITEM,
  CART_SAVE_PAYMENT_METHOD,
  CART_SAVE_SHIPPING_ADDRESS,
  CART_UPDATE_QTY,
} from "../Constants/CartConstants";

export const addToCart = (id, qty, typePay) => async (dispatch, getState) => {
  const { data } = await axios.get(`/api/products/${id}`);
  
  const productData = data.data;

  dispatch({
    type: CART_ADD_ITEM,
    payload: {
      product: productData.id,
      name: productData.name,
      image: productData.image,
      price: productData.price,
      countInStock: productData.countInStock,
      loanPrice: productData.loanPrice,
      typePay: typePay,
      qty,
    },
  });
  localStorage.removeItem("cartItems");
  localStorage.setItem("typePay", typePay);
  localStorage.setItem("cartItems", JSON.stringify(getState().cart.cartItems));
};

export const updateCartQty = (id, qty) => (dispatch, getState) => {
  dispatch({ type: CART_UPDATE_QTY, payload: { id, qty } });
  localStorage.setItem("cartItems", JSON.stringify(getState().cart.cartItems));
};

export const removefromcart = (id) => (dispatch, getState) => {
  dispatch({
    type: CART_REMOVE_ITEM,
    payload: id,
  });

  localStorage.setItem("cartItems", JSON.stringify(getState().cart.cartItems));
};

export const saveShippingAddress = (data) => (dispatch) => {
  dispatch({
    type: CART_SAVE_SHIPPING_ADDRESS,
    payload: data,
  });

  localStorage.setItem("shippingAddress", JSON.stringify(data));
};

export const savePaymentMethod = (data) => (dispatch) => {
  dispatch({
    type: CART_SAVE_PAYMENT_METHOD,
    payload: data,
  });

  localStorage.setItem("paymentMethod", JSON.stringify(data));
};
