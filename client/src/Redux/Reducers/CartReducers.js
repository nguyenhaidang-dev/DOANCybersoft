import {
  CART_ADD_ITEM,
  CART_CLEAR_ITEMS,
  CART_REMOVE_ITEM,
  CART_SAVE_PAYMENT_METHOD,
  CART_SAVE_SHIPPING_ADDRESS,
  CART_UPDATE_QTY,
} from "../Constants/CartConstants";

export const cartReducer = (
  state = { cartItems: [], shippingAddress: {}, typePay: "" },
  action
) => {
  switch (action.type) {
    case CART_ADD_ITEM:
      const item = action.payload;

      const existItem = state.cartItems.find((x) => x.product === item.product);

      if (existItem) {
        const newQty = Math.min(
          existItem.qty + item.qty,
          item.countInStock
        );
        return {
          ...state,
          typePay: item.typePay,
          cartItems: state.cartItems.map((x) =>
            x.product === existItem.product ? { ...item, qty: newQty } : x
          ),
        };
      } else {
        return {
          ...state,
          typePay: item.typePay,
          cartItems: [...state.cartItems, item],
        };
      }
    case CART_UPDATE_QTY:
      return {
        ...state,
        cartItems: state.cartItems.map((x) =>
          x.product === action.payload.id
            ? { ...x, qty: Math.min(Math.max(1, action.payload.qty), x.countInStock) }
            : x
        ),
      };
    case CART_REMOVE_ITEM:
      return {
        ...state,
        cartItems: state.cartItems.filter((x) => x.product !== action.payload),
      };
    case CART_SAVE_SHIPPING_ADDRESS:
      return {
        ...state,
        shippingAddress: action.payload,
      };
    case CART_SAVE_PAYMENT_METHOD:
      return {
        ...state,
        paymentMethod: action.payload,
      };
    case CART_CLEAR_ITEMS:
      return {
        ...state,
        cartItems: [],
      };
    default:
      return state;
  }
};
