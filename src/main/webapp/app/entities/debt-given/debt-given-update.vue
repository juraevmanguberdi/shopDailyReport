<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="shopDailyReportApp.debtGiven.home.createOrEditLabel"
          data-cy="DebtGivenCreateUpdateHeading"
          v-text="$t('shopDailyReportApp.debtGiven.home.createOrEditLabel')"
        >
          Create or edit a DebtGiven
        </h2>
        <div>
          <div class="form-group" v-if="debtGiven.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="debtGiven.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.debtGiven.client')" for="debt-given-client">Client</label>
            <select class="form-control" id="debt-given-client" data-cy="client" name="client" v-model="debtGiven.client" required>
              <option v-if="!debtGiven.client" v-bind:value="null" selected></option>
              <option
                v-bind:value="debtGiven.client && clientOption.id === debtGiven.client.id ? debtGiven.client : clientOption"
                v-for="clientOption in clients"
                :key="clientOption.id"
              >
                {{ clientOption.name + " " + clientOption.surName }}
              </option>
            </select>
          </div>
          <div v-if="$v.debtGiven.client.$anyDirty && $v.debtGiven.client.$invalid">
            <small class="form-text text-danger" v-if="!$v.debtGiven.client.required" v-text="$t('entity.validation.required')">
              This field is required.
            </small>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.debtGiven.debtAmount')" for="debt-given-debtAmount"
              >Debt Amount</label
            >
            <input
              type="number"
              class="form-control"
              name="debtAmount"
              id="debt-given-debtAmount"
              data-cy="debtAmount"
              :class="{ valid: !$v.debtGiven.debtAmount.$invalid, invalid: $v.debtGiven.debtAmount.$invalid }"
              v-model.number="$v.debtGiven.debtAmount.$model"
              required
            />
            <div v-if="$v.debtGiven.debtAmount.$anyDirty && $v.debtGiven.debtAmount.$invalid">
              <small class="form-text text-danger" v-if="!$v.debtGiven.debtAmount.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.debtGiven.debtAmount.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.debtGiven.debtDate')" for="debt-given-debtDate"
              >Debt Date</label
            >
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="debt-given-debtDate"
                  v-model="$v.debtGiven.debtDate.$model"
                  name="debtDate"
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
                id="debt-given-debtDate"
                data-cy="debtDate"
                type="text"
                class="form-control"
                name="debtDate"
                :class="{ valid: !$v.debtGiven.debtDate.$invalid, invalid: $v.debtGiven.debtDate.$invalid }"
                v-model="$v.debtGiven.debtDate.$model"
                required
              />
            </b-input-group>
            <div v-if="$v.debtGiven.debtDate.$anyDirty && $v.debtGiven.debtDate.$invalid">
              <small class="form-text text-danger" v-if="!$v.debtGiven.debtDate.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.debtGiven.returnDate')" for="debt-given-returnDate"
              >Return Date</label
            >
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="debt-given-returnDate"
                  v-model="$v.debtGiven.returnDate.$model"
                  name="returnDate"
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
                id="debt-given-returnDate"
                data-cy="returnDate"
                type="text"
                class="form-control"
                name="returnDate"
                :class="{ valid: !$v.debtGiven.returnDate.$invalid, invalid: $v.debtGiven.returnDate.$invalid }"
                v-model="$v.debtGiven.returnDate.$model"
                required
              />
            </b-input-group>
            <div v-if="$v.debtGiven.returnDate.$anyDirty && $v.debtGiven.returnDate.$invalid">
              <small class="form-text text-danger" v-if="!$v.debtGiven.returnDate.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.debtGiven.code')" for="debt-given-code">Code</label>
            <input
              type="text"
              class="form-control"
              name="code"
              id="debt-given-code"
              data-cy="code"
              :class="{ valid: !$v.debtGiven.code.$invalid, invalid: $v.debtGiven.code.$invalid }"
              v-model="$v.debtGiven.code.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.debtGiven.notes')" for="debt-given-notes">Notes</label>
            <input
              type="text"
              class="form-control"
              name="notes"
              id="debt-given-notes"
              data-cy="notes"
              :class="{ valid: !$v.debtGiven.notes.$invalid, invalid: $v.debtGiven.notes.$invalid }"
              v-model="$v.debtGiven.notes.$model"
            />
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
            :disabled="$v.debtGiven.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./debt-given-update.component.ts"></script>
