import dayjs from 'dayjs';
import 'dayjs/locale/vi'; // 🇻🇳 import locale tiếng Việt
import relativeTime from 'dayjs/plugin/relativeTime';

dayjs.extend(relativeTime);
dayjs.locale('vi'); // Đặt ngôn ngữ là tiếng Việt

export default dayjs;