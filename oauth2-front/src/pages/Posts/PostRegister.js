import React, { useRef, useState } from 'react';
import { useQuery } from 'react-query';
import axios from 'axios';


const PostRegister = () => {
    const [ imgFiles, setImgFiles ] = useState([]);
    const fileId = useRef(1);
    

    const principal = useQuery(["principal"], async () => {
        const option = {
            headers: {
                Authorization: `Bearer ${localStorage.getItem("accessToken")}`
            }
        }
        const response = await axios.get("http://localhost:8080/account/principal", option);
        return response;
    });

    if(principal.isLoading) {
        return <>...Loading</>;
    }

    const addFileHandle = (e) => {
        const newImgFiles = [];

        for(const file of e.target.files) {
            const fileData = {
                id: fileId.current,
                file
            }
            fileId.current += 1;
            newImgFiles.push(fileData);
        }

        setImgFiles([...imgFiles, ...newImgFiles]);
    }

    return (
        <div>
            <h3>제목</h3>
            <input type="text" />
            <h3>작성자</h3>
            <input type="text" value={principal.data.data.name} disabled={true} />
            <h3>내용</h3>
            <textarea cols="30" rows="10"></textarea>
            <h3>이미지파일</h3>
            <input type="file" multiple={true} onChange={addFileHandle} />
            <ul>
                {imgFiles.map(imgFile => <li key={imgFile.id}>{imgFile.file.name} <button>삭제</button></li>)}
            </ul>
        </div>
    );
};

export default PostRegister;