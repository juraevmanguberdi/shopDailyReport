<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="shopDailyReportApp.borrowedDebt.home.createOrEditLabel"
          data-cy="BorrowedDebtCreateUpdateHeading"
          v-text="$t('shopDailyReportApp.borrowedDebt.home.createOrEditLabel')"
        >
          Create or edit a BorrowedDebt
        </h2>
        <div>
          <div class="form-group" v-if="borrowedDebt.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="borrowedDebt.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.borrowedDebt.amount')" for="borrowed-debt-amount"
              >Amount</label
            >
            <input
              type="number"
              class="form-control"
              name="amount"
              id="borrowed-debt-amount"
              data-cy="amount"
              :class="{ valid: !$v.borrowedDebt.amount.$invalid, invalid: $v.borrowedDebt.amount.$invalid }"
              v-model.number="$v.borrowedDebt.amount.$model"
              required
            />
            <div v-if="$v.borrowedDebt.amount.$anyDirty && $v.borrowedDebt.amount.$invalid">
              <small class="form-text text-danger" v-if="!$v.borrowedDebt.amount.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.borrowedDebt.amount.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.borrowedDebt.notes')" for="borrowed-debt-notes">Notes</label>
            <input
              type="text"
              class="form-control"
              name="notes"
              id="borrowed-debt-notes"
              data-cy="notes"
              :class="{ valid: !$v.borrowedDebt.notes.$invalid, invalid: $v.borrowedDebt.notes.$invalid }"
              v-model="$v.borrowedDebt.notes.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.borrowedDebt.code')" for="borrowed-debt-code">Code</label>
            <input
              type="text"
              class="form-control"
              name="code"
              id="borrowed-debt-code"
              data-cy="code"
              :class="{ valid: !$v.borrowedDebt.code.$invalid, invalid: $v.borrowedDebt.code.$invalid }"
              v-model="$v.borrowedDebt.code.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.borrowedDebt.date')" for="borrowed-debt-date">Date</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="borrowed-debt-date"
                  v-model="$v.borrowedDebt.date.$model"
                  name="date"
                  class="form-control"
                  :locale="currentLanguage"
                  button-only
                  today-button
                  reset-button
                  close-button
                >
                </b-form-datepicker>
              </b-input-group-prepend>
              <b-form-input
                id="borrowed-debt-date"
                data-cy="date"
                type="text"
                class="form-control"
                name="date"
                :class="{ valid: !$v.borrowedDebt.date.$invalid, invalid: $v.borrowedDebt.date.$invalid }"
                v-model="$v.borrowedDebt.date.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('shopDailyReportApp.borrowedDebt.borrowedDebtType')"
              for="borrowed-debt-borrowedDebtType"
              >Borrowed Debt Type</label
            >
            <select
              class="form-control"
              id="borrowed-debt-borrowedDebtType"
              data-cy="borrowedDebtType"
              name="borrowedDebtType"
              v-model="borrowedDebt.borrowedDebtType"
              required
            >
              <option v-if="!borrowedDebt.borrowedDebtType" v-bind:value="null" selected></option>
              <option
                v-bind:value="
                  borrowedDebt.borrowedDebtType && borrowedDebtTypeOption.id === borrowedDebt.borrowedDebtType.id
                    ? borrowedDebt.borrowedDebtType
                    : borrowedDebtTypeOption
                "
                v-for="borrowedDebtTypeOption in borrowedDebtTypes"
                :key="borrowedDebtTypeOption.id"
              >
                {{ borrowedDebtTypeOption.id }}
              </option>
            </select>
          </div>
          <div v-if="$v.borrowedDebt.borrowedDebtType.$anyDirty && $v.borrowedDebt.borrowedDebtType.$invalid">
            <small
              class="form-text text-danger"
              v-if="!$v.borrowedDebt.borrowedDebtType.required"
              v-text="$t('entity.validation.required')"
            >
              This field is required.
            </small>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.borrowedDebt.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./borrowed-debt-update.component.ts"></script>
