import axios from "axios";

export const checkImage = (file) => {
  let err = "";
  if (!file) return (err = "File does not exist.");

  if (file.size > 1024 * 1024)
    err = "The largest image size is 1mb.";

  if (file.type !== "image/jpeg" && file.type !== "image/png")
    err = "Image format is incorrect.";

  return err;
};

export const imageUpload = async (image) => {
  if (!image || !(image instanceof Blob)) return null;

  const formData = new FormData();
  formData.append("file", image);

  const { data } = await axios.post("/api/upload", formData, {
    headers: { "Content-Type": "multipart/form-data" },
  });

  return data.data;
};

export const imageShow = (src) => {
  return (
    <img
      src={src}
      alt="images"
      style={{ height: "150px", width: "150px", objectFit: "cover" }}
      className="img-thumbnail"
    />
  );
};

export const monthNames = (months) => {
  return months.map((month) => {
    const date = new Date(Date.UTC(2000, month - 1, 1));
    return date.toLocaleString('en-US', { month: 'long' });
  });
}
