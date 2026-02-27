import Carousel from "react-elastic-carousel";
import React from "react";

const BANNERS = [
  { id: 1, linkImg: "/images/banner1.png", linkPage: "/", alt: "Banner 1" },
  { id: 2, linkImg: "/images/banner2.png", linkPage: "/", alt: "Banner 2" },
  { id: 3, linkImg: "/images/banner3.png", linkPage: "/", alt: "Banner 3" },
];

const Slide = () => {
  return (
    <Carousel
      itemsToShow={1}
      enableAutoPlay
      autoPlaySpeed={4000}
      pagination={true}
      showArrows={true}
    >
      {BANNERS.map((item) => (
        <div key={item.id} style={{ width: "100%" }}>
          <a href={item.linkPage} rel="noreferrer">
            <img
              className="slide"
              src={item.linkImg}
              alt={item.alt}
              style={{ width: "100%", objectFit: "cover" }}
            />
          </a>
        </div>
      ))}
    </Carousel>
  );
};

export default Slide;
