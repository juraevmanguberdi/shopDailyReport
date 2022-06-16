export interface IOwnerExpenseType {
  id?: number;
  name?: string;
  code?: string | null;
  notes?: string | null;
  createdDate?: Date | null;
}

export class OwnerExpenseType implements IOwnerExpenseType {
  constructor(
    public id?: number,
    public name?: string,
    public code?: string | null,
    public notes?: string | null,
    public createdDate?: Date | null
  ) {}
}
