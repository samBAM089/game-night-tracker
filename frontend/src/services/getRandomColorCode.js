const getRandomColorCode = () => {
    const makeColorCode = '0123456789ABCDEF';
    let code = '#';
    for (let count = 0; count < 6; count++) {
        code = code + makeColorCode[Math.floor(Math.random() * 16)];
    }
    return code;
};

export default getRandomColorCode;
