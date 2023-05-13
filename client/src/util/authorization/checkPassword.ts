// It checks for a sequence of characters that starts with one or more non-whitespace characters ([^\s@]+),
// followed by an @ symbol, followed by one or more non-whitespace characters for the domain name ([^\s@]+),
// then a . character, and finally one or more non-whitespace characters for the top-level domain ([^\s@]+).
export const checkEmail = (email: string) => {
  const regexp = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  return regexp.test(email);
};

export const checkPassword = (password: string) => {
  const regexp = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z0-9^\W_]{8,20}$/;
  return regexp.test(password);
};
