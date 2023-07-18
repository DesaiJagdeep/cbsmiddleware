export interface IActivityType {
  id: number;
  activityType?: string | null;
  activityTypeCode?: number | null;
}

export type NewActivityType = Omit<IActivityType, 'id'> & { id: null };
