import React from "react";
import CreateCategory from "./CreateCategory";
import CategoriesTable from "./CategoriesTable";
import { useState, useEffect } from "react";
import axios from "axios";
import { Link } from "react-router-dom";
import { toast } from "react-toastify";

const ToastObjects = {
  pauseOnFocusLoss: false,
  draggable: false,
  pauseOnHover: false,
  autoClose: 2000,
};

const MainCategories = () => {
  const [formCategory, setFormCategory] = useState({
    name: "",
    isShow: false,
    description: "",
    parentCategory: "",
  });

  const [listCategory, setListCategory] = useState([]);
  const [idEdit, setIdEdit] = useState(-1);

  const handleChangeForm = (key, value) => {
    setFormCategory({
      ...formCategory,
      [key]: value,
    });
  };

  useEffect(async () => {
    await fetchData();
  }, []);

  const fetchData = async () => {
    const res = await axios.get("/api/category/all");
    if (res.status === 200) {
      const cats = res.data?.data ?? res.data;
      setListCategory(Array.isArray(cats) ? cats : []);
    }
  };

  const cleanForm = () => {
    setFormCategory({
      name: "",
      isShow: false,
      description: "",
    });
  };

  const submitForm = async (e) => {
    e.preventDefault();
    try {
      const res = await axios.post("/api/category", {
        name: formCategory.name,
        isShow: formCategory.isShow,
        description: formCategory.description,
        parentCategory: formCategory.parentCategory || null,
      });
      if (res.status === 201) {
        toast.success("Tạo danh mục thành công", ToastObjects);
        await fetchData();
        cleanForm();
      }
    } catch (err) {
      const msg = err?.response?.data?.message || "Tạo danh mục thất bại";
      toast.error(msg, ToastObjects);
    }
  };

  const updateForm = async (e) => {
    e.preventDefault();
    try {
      const res = await axios.put(`/api/category/${idEdit}`, {
        name: formCategory.name,
        isShow: formCategory.isShow,
        description: formCategory.description,
      });
      if (res.status === 200) {
        toast.success("Cập nhật danh mục thành công", ToastObjects);
        await fetchData();
        cleanForm();
        setIdEdit(-1);
      }
    } catch (err) {
      const msg = err?.response?.data?.message || "Cập nhật thất bại";
      toast.error(msg, ToastObjects);
    }
  };

  const handleChangeShowItem = async (id, showItem) => {
    const res = await axios.put(`/api/category/status/${id}`, {
      isShow: !showItem,
    });
    if (res.status === 200) {
      toast.success("Cập nhật trạng thái thành công !!");
      await fetchData();
    }
  };

  const deleteItem = async (id) => {
    const res = await axios.delete(`/api/category/${id}`);
    if (res.status === 200) {
      toast.success("Xóa Danh Mục Thành Công !!");
      await fetchData();
    }
  };

  const handleEditItem = async (id) => {
    const res = await axios.get(`/api/category/${id}`);
    if (res.status === 200) {
      const cat = res.data?.data ?? res.data;
      setFormCategory({
        name: cat.name,
        isShow: cat.isShow,
        description: cat.description,
      });
      setIdEdit(cat.id);
    }
  };

  return (
    <section className="content-main">
      <div className="content-header">
        <h2 className="content-title">DANH MỤC SẢN PHẨM</h2>
      </div>

      <div className="card shadow-sm">
        <div className="card-body">
          <div className="row">
            {/* Create category */}
            <div className="col-md-12 col-lg-4">
              <form>
                <div className="mb-4">
                  <label htmlFor="product_name" className="form-label">
                    Tên danh mục Cha (Nếu Cần)
                  </label>
                  <select
                    className="form-control"
                    onChange={(e) =>
                      handleChangeForm("parentCategory", e.target.value)
                    }
                  >
                    <option value="">-- default ---</option>
                    {listCategory.map((e) => (
                      <option key={e.id} value={e.id}>
                        {e.name}
                      </option>
                    ))}
                  </select>
                </div>
                <div className="mb-4">
                  <label htmlFor="product_name" className="form-label">
                    Tên danh mục
                  </label>
                  <input
                    type="text"
                    placeholder="Type here"
                    className="form-control py-3"
                    id="product_name"
                    value={formCategory.name}
                    onChange={(e) => handleChangeForm("name", e.target.value)}
                  />
                </div>
                <div className="mb-4">
                  <label className="form-label">Hiển Thị</label>
                  <div className="form-check">
                    <input
                      className="form-check-input"
                      type="checkbox"
                      checked={formCategory.isShow ? true : false}
                      onChange={(e) =>
                        handleChangeForm("isShow", !formCategory.isShow)
                      }
                    />
                  </div>
                </div>
                <div className="mb-4">
                  <label className="form-label">Mô tả</label>
                  <textarea
                    placeholder="Type here"
                    className="form-control"
                    rows="4"
                    value={formCategory.description}
                    onChange={(e) =>
                      handleChangeForm("description", e.target.value)
                    }
                  ></textarea>
                </div>

                <div className="d-grid">
                  {idEdit !== -1 ? (
                    <button
                      className="btn btn-primary py-3"
                      onClick={(e) => updateForm(e)}
                    >
                      Cập nhật danh mục
                    </button>
                  ) : (
                    <button
                      className="btn btn-primary py-3"
                      onClick={(e) => submitForm(e)}
                    >
                      Tạo danh mục
                    </button>
                  )}
                </div>
              </form>
            </div>
            {/* Categories table */}
            <div className="col-md-12 col-lg-8">
              <table className="table">
                <thead>
                  <tr>
                    <th>#</th>
                    <th>Tên danh mục</th>
                    <th>Loại</th>
                    <th>Hiển thị</th>
                    <th>Mô tả</th>
                    <th className="text-end">Action</th>
                  </tr>
                </thead>
                {/* Table Data */}
                <tbody>
                  {listCategory?.map((item, index) => (
                    <tr key={item.id}>
                      <td>{item.id}</td>
                      <td>
                        <b>{item.isParent ? item.name : <span style={{paddingLeft:'16px'}}>↳ {item.name}</span>}</b>
                      </td>
                      <td>
                        <span style={{fontSize:'12px', padding:'2px 6px', borderRadius:'4px', background: item.isParent ? '#d1fae5' : '#e0f2fe', color: item.isParent ? '#065f46' : '#0369a1'}}>
                          {item.isParent ? 'Danh mục cha' : 'Danh mục con'}
                        </span>
                      </td>
                      <td
                        onClick={() =>
                          handleChangeShowItem(item.id, item.isShow)
                        }
                      >
                        {item.isShow ? (
                          <span style={{ color: "green", cursor: "pointer" }}>
                            Đang Hiển Thị
                          </span>
                        ) : (
                          <span style={{ color: "red", cursor: "pointer" }}>
                            Đang Ẩn
                          </span>
                        )}
                      </td>
                      <td>{item.description}</td>
                      <td className="text-end">
                        <div className="dropdown">
                          <Link
                            to="#"
                            data-bs-toggle="dropdown"
                            className="btn btn-light"
                          >
                            <i className="fas fa-ellipsis-h"></i>
                          </Link>
                          <div className="dropdown-menu">
                            <div
                              className="dropdown-item"
                              onClick={() => handleEditItem(item.id)}
                            >
                              Chỉnh sửa
                            </div>
                            <div
                              className="dropdown-item text-danger"
                              onClick={() => deleteItem(item.id)}
                            >
                              Xóa
                            </div>
                          </div>
                        </div>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
};

export default MainCategories;
