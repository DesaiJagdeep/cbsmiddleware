import dayjs from 'dayjs/esm';

export interface INotification {
  id: number;
  title?: string | null;
  content?: string | null;
  isRead?: boolean | null;
  createdAt?: dayjs.Dayjs | null;
  recipient?: string | null;
  sender?: string | null;
  type?: string | null;
}

export type NewNotification = Omit<INotification, 'id'> & { id: null };
