let _setIsLoading = null;

export const setLoadingSetter = (fn) => {
  _setIsLoading = fn;
};

export const setGlobalLoading = (value) => {
    console.log(value, _setIsLoading)
  if (_setIsLoading) _setIsLoading(value);
};
