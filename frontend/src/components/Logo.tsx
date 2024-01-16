import { Image } from "antd";
import { Link } from "react-router-dom";
import LogoImage from "../assets/logo.webp";

function Logo() {
  return (
    <Link to="/">
      <Image src={LogoImage} preview={false} alt="Logo Image" style={{ height: "2.5em" }} />
    </Link>
  );
}

export default Logo;
