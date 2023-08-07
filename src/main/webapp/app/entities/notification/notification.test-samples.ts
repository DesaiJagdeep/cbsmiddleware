import dayjs from 'dayjs/esm';

import { INotification, NewNotification } from './notification.model';

export const sampleWithRequiredData: INotification = {
  id: 30621,
};

export const sampleWithPartialData: INotification = {
  id: 3799,
  isRead: false,
  createdAt: dayjs('2023-08-07T12:27'),
  recipient: 'Towels',
  sender: 'Berkshire',
};

export const sampleWithFullData: INotification = {
  id: 32606,
  title: 'quantify magenta',
  content: 'Incredible',
  isRead: true,
  createdAt: dayjs('2023-08-06T18:54'),
  recipient: 'Object-based Salad',
  sender: 'empowering Chicken',
  type: 'deposit Bedfordshire Gorgeous',
};

export const sampleWithNewData: NewNotification = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
