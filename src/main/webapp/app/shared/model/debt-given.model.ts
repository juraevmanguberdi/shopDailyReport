import { IClient } from '@/shared/model/client.model';

export interface IDebtGiven {
  id?: number;
  debtAmount?: number;
  debtDate?: Date;
  returnDate?: Date;
  code?: string | null;
  notes?: string | null;
  client?: IClient;
}

export class DebtGiven implements IDebtGiven {
  constructor(
    public id?: number,
    public debtAmount?: number,
    public debtDate?: Date,
    public returnDate?: Date,
    public code?: string | null,
    public notes?: string | null,
    public client?: IClient
  ) {}
}
