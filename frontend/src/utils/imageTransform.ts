import { RcFile } from "antd/es/upload";

export const getBase64 = (img: RcFile): Promise<string> => {
  return new Promise(res => {
    const reader = new FileReader();
    reader.addEventListener("load", () => res(reader.result as string));
    reader.readAsDataURL(img);
  });
};
