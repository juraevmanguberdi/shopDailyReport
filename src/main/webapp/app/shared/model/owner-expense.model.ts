import { IOwnerExpenseType } from '@/shared/model/owner-expense-type.model';

export interface IOwnerExpense {
  id?: number;
  amount?: number;
  code?: string | null;
  expenseDate?: Date | null;
  notes?: string | null;
  ownerExpenseType?: IOwnerExpenseType;
}

export class OwnerExpense implements IOwnerExpense {
  constructor(
    public id?: number,
    public amount?: number,
    public code?: string | null,
    public expenseDate?: Date | null,
    public notes?: string | null,
    public ownerExpenseType?: IOwnerExpenseType
  ) {}
}
