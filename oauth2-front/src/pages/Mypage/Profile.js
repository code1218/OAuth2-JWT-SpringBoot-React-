/** @jsxImportSource @emotion/react */
import { css } from "@emotion/react";
import axios from "axios";
import React, { useEffect, useRef, useState } from "react";
import { useMutation, useQuery } from "react-query";

const container = css`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 500px;
`;

const imgBox = css`
  display: flex;
  justify-content: center;
  align-items: center;
  border: 1px solid #dbdbdb;
  border-radius: 50%;
  width: 200px;
  height: 200px;
  overflow: hidden;
  cursor: pointer;
`;
const img = css`
  width: 100%;
`;
const fileInput = css`
  display: none;
`;

const Profile = () => {
  const [imgFile, setImgFile] = useState();
  const [profileImgURL, setProfileImgURL] = useState();
  const fileRef = useRef();

  const principal = useQuery(["principal"], async () => {
    const option = {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
      },
    };
    const response = await axios.get(
      "http://localhost:8080/account/principal",
      option
    );
    setProfileImgURL(
      "http://localhost:8080/image/profile/" + response.data.profileImg
    );
    return response;
  });

  const profileImgSubmit = useMutation(
    async () => {
      const formData = new FormData();
      formData.append("profileImgFile", imgFile);

      const option = {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
          "Content-Type": "multipart/form-data",
        },
      };
      const response = await axios.post(
        "http://localhost:8080/account/profile/img",
        formData,
        option
      );
      return response;
    },
    {
      onSuccess: () => {
        principal.refetch();
      },
    }
  );

  const profileImgChangeHandle = () => {
    fileRef.current.click();
  };

  const profileImgFileChangeHandle = (e) => {
    setImgFile(e.target.files[0]);

    const fileReader = new FileReader();

    fileReader.onload = (event) => {
      setProfileImgURL(event.target.result);
    };

    fileReader.readAsDataURL(e.target.files[0]);
    e.target.value = null;
  };

  return (
    <div css={container}>
      <div css={imgBox} onClick={profileImgChangeHandle}>
        <img css={img} src={profileImgURL} alt="" />
        <input
          css={fileInput}
          type="file"
          onChange={profileImgFileChangeHandle}
          ref={fileRef}
        />
      </div>
      <button onClick={() => profileImgSubmit.mutate()}>이미지 저장</button>
    </div>
  );
};

export default Profile;
