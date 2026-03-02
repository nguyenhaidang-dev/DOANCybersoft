import React from "react";
import { Link } from "react-router-dom";
import { useDispatch } from "react-redux";
import { deleteProduct } from "../../Redux/Actions/ProductActions";
import { toast } from "react-toastify";

const Product = (props) => {
  const { product } = props;
  const dispatch = useDispatch();

  const deletehandler = (id) => {
    toast(
      ({ closeToast }) => (
        <div>
          <p className="mb-2">Xóa sản phẩm này?</p>
          <button
            className="btn btn-danger btn-sm me-2"
            onClick={() => { dispatch(deleteProduct(id)); closeToast(); }}
          >Xóa</button>
          <button className="btn btn-secondary btn-sm" onClick={closeToast}>Hủy</button>
        </div>
      ),
      { autoClose: false, closeOnClick: false }
    );
  };

  return (
    <>
      <div className="col-md-6 col-sm-6 col-lg-3 mb-5">
        <div className="card card-product-grid shadow-sm">
          <Link to="#" className="img-wrap">
            <img src={product.image} alt="Product" />
          </Link>
          <div className="info-wrap">
            <Link to="#" className="title text-truncate">
              {product.name} - {product.ma}
            </Link>
            <div className="price mb-2">{product.price}VND</div>
            <div className="row">
              <Link
                to={`/product/${product.id}/edit`}
                className="btn btn-sm btn-outline-success p-2 pb-3 col-md-6"
              >
                <i className="fas fa-pen"></i>
              </Link>
              <button
                onClick={() => deletehandler(product.id)}
                className="btn btn-sm btn-outline-danger p-2 pb-3 col-md-6"
              >
                <i className="fas fa-trash-alt"></i>
              </button>
            </div>
          </div>
        </div>
      </div>
    </>
  );
};

export default Product;
